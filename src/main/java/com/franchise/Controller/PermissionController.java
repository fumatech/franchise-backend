package com.franchise.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.franchise.Entity.Permission;
import com.franchise.Service.PermissionService;

@RestController
@RequestMapping("/permissions")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, 
//allowedHeaders = "*",
//allowCredentials = "true")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	public PermissionController(PermissionService permissionService) {
		super();
		this.permissionService = permissionService;
	}

	public PermissionController() {
		super();
	}
	

	@PostMapping("/save")
	public ResponseEntity<Permission> savePermission(@RequestBody Permission permission) {
		Permission savedPermission = permissionService.savePermission(permission);
		return new ResponseEntity<>(savedPermission, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Permission>> getAllPermissions() {
		List<Permission> permissions = permissionService.getAllPermissions();
		return new ResponseEntity<>(permissions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
		Permission permission = permissionService.getPermissionById(id);
		return new ResponseEntity<>(permission, HttpStatus.OK);
	}
}
