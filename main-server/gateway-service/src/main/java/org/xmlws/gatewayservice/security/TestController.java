package org.xmlws.gatewayservice.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/role/required/test")
public class TestController {

	@RequestMapping(method = RequestMethod.GET)
	public String adminTest(HttpServletRequest request) {
		return "ADMIN TOKEN: " + request.getHeader("Authorization");
	}
}