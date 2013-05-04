package com.localjobs.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {


	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public void home(Principal currentUser, HttpServletRequest request,HttpServletResponse response)  throws Exception{
		
		response.getWriter().print("Currently logged in user "+currentUser.getName());
	}

	
}
