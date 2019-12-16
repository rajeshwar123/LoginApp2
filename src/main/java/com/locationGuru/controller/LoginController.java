package com.locationGuru.controller;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.locationGuru.dto.UserDTO;
import com.locationGuru.service.LoginService;

@Controller
public class LoginController {

	public LoginController() {
		System.out.println("LoginController.LoginController()");
	}

	@Autowired
	private LoginService service;

	@RequestMapping(value = "/login")
	public String showlogin() {
		return "login";
	}
	

	@RequestMapping(value="/login.htm",method=RequestMethod.POST)
	public String login(Model model,@RequestParam("uname") String username,@RequestParam("pwd") String password) {
			boolean flag = false;
			UserDTO dto = null;
			String role = null;
			RequestDispatcher rd = null;
			
			// using Service validating user
			try {
				flag = service.validate(username, password);
				if (flag == true) {
					role = service.getRole(username, password);
					if (role.equalsIgnoreCase("admin")) {
						// admin
						dto = service.getFullname(username);
						model.addAttribute("firstName", dto.getName());
						model.addAttribute("lastName", dto.getLastName());
						return "admin";
					} else if (role.equalsIgnoreCase("manager")) {
						// manager
						dto = service.getFullname(username);
						model.addAttribute("firstName", dto.getName());
						model.addAttribute("lastName", dto.getLastName());
						return "manager";
					} else {
						// employee
						dto = service.getFullname(username);
						model.addAttribute("firstName", dto.getName());
						model.addAttribute("lastName", dto.getLastName());
						
						return "employee";
					}
				} // if
				/*
				 * else { rd = req.getRequestDispatcher("error.jsp"); rd.forward(req, res); }
				 */
			} // try
			catch (SQLException se) {
				se.printStackTrace();
				return "error";
			} catch (ClassNotFoundException cnf) {
				cnf.printStackTrace();
				return "error";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}
			return "error";
		}// loginController
}
