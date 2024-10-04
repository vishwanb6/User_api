package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@RequestMapping("/t")
	public String test() {
	    System.out.println("test method called");
	    return "working fine";
	}
	
	@RequestMapping("/t2")
	public String test2() {
	    System.out.println("test 2 method called");
	    return "testing 2 fine";
	}
	

}
