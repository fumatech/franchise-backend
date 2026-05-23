package com.franchise.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/shift")
public class ShiftController {
    // TODO: Implement shift endpoints
}
