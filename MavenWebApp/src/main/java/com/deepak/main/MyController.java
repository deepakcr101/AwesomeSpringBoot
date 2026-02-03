package com.deepak.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

	// @RequestMapping(value = "/homePage",method=RequestMethod.GET)
	@GetMapping("/home")
	public ModelAndView openHomePage() {
		System.out.println("Inside homePage method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		// modelAndView.addObject("message", "Welcome to the Home Page!");
		return modelAndView;
	}

	@GetMapping("/aboutUs")
	public ModelAndView openAboutUsPage() {
		System.out.println("Inside aboutUsPage method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("aboutUs");
		// modelAndView.addObject("message", "Welcome to the About Us Page!");
		return modelAndView;
	}

}
