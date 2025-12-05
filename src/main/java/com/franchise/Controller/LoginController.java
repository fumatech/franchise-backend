package com.franchise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.LoginRequest;
import com.franchise.Entity.LoginResponse;
import com.franchise.Service.LoginService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://fusionmastertech.com", "https://fusionmastertech.com", "http://localhost:3000",
		"http://localhost:3001" }, allowCredentials = "true")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/loginn")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = loginService.login(request.getEmail(), request.getPassword());
		if ("success".equals(response.getStatus())) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
}
