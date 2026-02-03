package com.deepak.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.deepak.beans.User;

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

	@GetMapping("/myform")
	public String openContactUsPage() {
		return "myform";
	}

	/*
	 * @PostMapping("/submitForm") public String handleSubmitForm(HttpServletRequest
	 * req) { System.out.println("Inside handleSubmitForm method"); String name =
	 * req.getParameter("name"); String email = req.getParameter("email"); String
	 * message = req.getParameter("message");
	 * 
	 * System.out.println("Name: " + name); System.out.println("Email: " + email);
	 * System.out.println("Message: " + message);
	 * 
	 * return "profile"; }
	 */

	/*
	 * @PostMapping("/submitForm") public String handleSubmitForm(@RequestParam(name
	 * = "name", required = true) String name,
	 * 
	 * @RequestParam(name = "email", required = true) String
	 * email, @RequestParam(name = "message") String message, Model model) {
	 * 
	 * User user = new User(); user.setName(name); user.setEmail(email);
	 * user.setMessage(message); model.addAttribute("user", user);
	 * 
	 * return "profile"; }
	 */

	@PostMapping("/submitForm")
	public String handleSubmitForm(@ModelAttribute User user) {

		return "profile";
	}

}
