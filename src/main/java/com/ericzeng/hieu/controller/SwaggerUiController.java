package com.ericzeng.hieu.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class SwaggerUiController {

    private static final String PREFIX = "/swagger-ui";
    
    public static final int BYTE_BUFFER_SIZE = 4096;

    private static Map<String, String> typeMap = new HashMap<>();

    {{
        typeMap.put("css", "text/css");
        typeMap.put("html", "text/html");
        typeMap.put("js", "application/javascript");
        typeMap.put("png", "image/png");
    }}

    private static String getExtension(String path) {
        int i = path.lastIndexOf(".");
        if (i >= 0) {
            return path.substring(i + 1);
        } else {
            return "";
        }
    }

    private static String getMimeType(String path) {
        String ext = getExtension(path).toLowerCase();
        String type = typeMap.get(ext);
        return type != null ? type : "application/octet-stream";
    }

    @RequestMapping(PREFIX + "/**")
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getPathInfo().substring(PREFIX.length());

        InputStream instr;

        if (path.equals("")) {
            response.setStatus(301);
            response.addHeader("Location", PREFIX + "/");
            return;
        } else if (path.equals("/")) {
            path = "/swagger-ui.html";
            instr = SwaggerUiController.class.getResourceAsStream("swagger-ui.html");
        } else {
            instr = SwaggerUiController.class.getClassLoader().getResourceAsStream("META-INF/resources" + path);
        }

        response.setContentType("text/plain");
        response.setStatus(200);

        if (instr != null) {
            response.setStatus(200);
            response.setContentType(getMimeType(path));
            copy(instr, response.getOutputStream());
        } else {
            response.setStatus(404);
            response.setContentType("text/plain");
            response.getOutputStream().println("Not found");
        }
    }
    
    public static void copy(InputStream in, OutputStream out) {

        try {
            byte[] buffer = new byte[BYTE_BUFFER_SIZE];
            while (true) {
                int count = in.read(buffer);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    out.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
