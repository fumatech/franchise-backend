package com.franchise.Controller;

import com.franchise.Entity.LoginRequest;
import com.franchise.Entity.User;
import com.franchise.Service.UserService;
import com.franchise.Tenant.TenantContextHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(
    origins = {
        "http://fusionmastertech.com",
        "https://fusionmastertech.com",
        "http://localhost:3000",
        "http://localhost:3001"
    },
    allowCredentials = "true"
)
public class UserController {

    @Autowired
    private UserService userservice;

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User savedUser = userservice.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
	    boolean isAuthenticated = userservice.authenticate(request.getEmail(), request.getPassword());
	    
	    if (isAuthenticated) {
	        // Create or get the session
	        session.setAttribute("userEmail", request.getEmail());
	        return new ResponseEntity<>("Login successful", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Invalid credentials or account inactive", HttpStatus.UNAUTHORIZED);
	    }
	}
	
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        TenantContextHolder.clear();
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userservice.getallusers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userservice.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userservice.updateUser(id, user)
                .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userservice.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserWithRolesAndPermissions(@PathVariable String email) {
        try {
            User user = userservice.getUserWithRolesAndPermissions(email);
            return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                              : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        Optional<User> exists = userservice.findByEmail(email);
        return exists.isPresent() ? ResponseEntity.ok("Email exists")
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist");
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(@RequestParam String email) {
        Optional<String> username = userservice.getUserName(email);
        return username.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}