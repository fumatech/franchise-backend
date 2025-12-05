package com.franchise.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.franchise.Tenant.TenantContextHolder;

import java.util.Map;

@RestController
@RequestMapping("/tenant")
@CrossOrigin(
    origins = {
        "http://fusionmastertech.com",
        "https://fusionmastertech.com",
        "http://localhost:3000",
        "http://localhost:3001"
    },
    allowCredentials = "true"
)
public class TenantConnectionController {

    @PostMapping("/connect")
    public ResponseEntity<String> connectToTenant(@RequestBody Map<String, String> body) {
        String tenantDb = body.get("tenantDbName");
        if (tenantDb == null || tenantDb.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing tenantDbName");
        }
        
        return ResponseEntity.ok("Tenant connection ready for: " + tenantDb);
    }

    @GetMapping("/debug")
    public ResponseEntity<String> getCurrentTenant() {
        String db = TenantContextHolder.getTenantDbName();
        return ResponseEntity.ok("Current tenant DB: " + db);
    }
}