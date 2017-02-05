package com.ericzeng.hieu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericzeng.hieu.entities.TestObject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class TestController {
	
    @ApiOperation(value = "Test", nickname = "test controller")
    @RequestMapping(value = "/test/getTestString", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public ResponseEntity<TestObject> getTestString(){
    	
    	TestObject to= new TestObject();
    	to.setReturnString("Hello World");
       
    	return new ResponseEntity<>(to, HttpStatus.OK);
    }

}
