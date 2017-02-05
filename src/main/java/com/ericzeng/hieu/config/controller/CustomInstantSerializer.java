package com.ericzeng.hieu.config.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;

public final class CustomInstantSerializer<T extends Temporal> extends StdSerializer<T> {
    /**
     * 
     */
    private static final long serialVersionUID = 940232697079616226L;

    /**
     * 
     */

    public static final CustomInstantSerializer<Instant> INSTANT = new CustomInstantSerializer<>(Instant.class,
            Instant::toEpochMilli, Instant::getEpochSecond, Instant::getNano);

    public static final CustomInstantSerializer<OffsetDateTime> OFFSET_DATE_TIME = new CustomInstantSerializer<>(
            OffsetDateTime.class, dt -> dt.toInstant().toEpochMilli(), OffsetDateTime::toEpochSecond,
            OffsetDateTime::getNano);

    public static final CustomInstantSerializer<ZonedDateTime> ZONED_DATE_TIME = new CustomInstantSerializer<>(
            ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(), ZonedDateTime::toEpochSecond,
            ZonedDateTime::getNano);

    private final ToLongFunction<T> getEpochMillis;

    private final ToLongFunction<T> getEpochSeconds;

    private final ToIntFunction<T> getNanoseconds;

    private CustomInstantSerializer(Class<T> supportedType, ToLongFunction<T> getEpochMillis,
            ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds) {
        super(supportedType);
        this.getEpochMillis = getEpochMillis;
        this.getEpochSeconds = getEpochSeconds;
        this.getNanoseconds = getNanoseconds;
    }

    @Override
    public void serializeWithType(T value, JsonGenerator generator, SerializerProvider provider,
            TypeSerializer serializer) throws IOException {
        serializer.writeTypePrefixForScalar(value, generator);
        this.serialize(value, generator, provider);
        serializer.writeTypeSuffixForScalar(value, generator);
    }

    public void serialize(T instant, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
            if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
                generator.writeNumber(DecimalUtils.toDecimal(this.getEpochSeconds.applyAsLong(instant),
                        this.getNanoseconds.applyAsInt(instant)));
            } else {
                generator.writeNumber(this.getEpochMillis.applyAsLong(instant));
            }
        } else {
            if (instant instanceof ZonedDateTime) {
                ZonedDateTime zonedDateTime = (ZonedDateTime) instant;
                generator.writeString(
                        serializeLocalTime((ZonedDateTime) instant) + zonedDateTime.getOffset().toString());
            } else {
                generator.writeString(instant.toString());
            }
        }
    }

    private String serializeLocalTime(ZonedDateTime zonedDateTime) {

        int yearValue = zonedDateTime.getYear();
        int monthValue = zonedDateTime.getMonthValue();
        int dayValue = zonedDateTime.getDayOfMonth();
        int absYear = Math.abs(yearValue);
        int hourValue = zonedDateTime.getHour();
        int minuteValue = zonedDateTime.getMinute();
        int secondValue = zonedDateTime.getSecond();

        StringBuilder buf = new StringBuilder(29);
        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append(yearValue - 10000).deleteCharAt(1);
            } else {
                buf.append(yearValue + 10000).deleteCharAt(0);
            }
        } else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }
        buf.append(monthValue < 10 ? "-0" : "-").append(monthValue).append(dayValue < 10 ? "-0" : "-").append(dayValue)
                .toString();

        buf.append('T');

        buf.append(hourValue < 10 ? "0" : "").append(hourValue).append(minuteValue < 10 ? ":0" : ":")
                .append(minuteValue);
        buf.append(secondValue < 10 ? ":0" : ":").append(secondValue);
        return buf.toString();

    }
}

