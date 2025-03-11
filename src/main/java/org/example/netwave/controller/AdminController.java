package org.example.netwave.controller;


import org.example.netwave.dto.AdminDTO;
import org.example.netwave.entity.Admin;
import org.example.netwave.service.AdminService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("save")
    public ResponseUtil saveAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO);
        return new ResponseUtil(201,"Admin Save Successfully",null);
    }

    @GetMapping("getAll")
    public ResponseUtil getAllAdmin() {
        return new ResponseUtil(200,"Load Admin List Successfully",adminService.getAllAdmins());
    }

    @PutMapping("update")
    public ResponseUtil updateAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(adminDTO);
        return new ResponseUtil(200,"Admin Update Successfully",null);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return new ResponseUtil(200,"Admin Delete Successfully",null);
    }

    @GetMapping("/checkRole")
    public String checkRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String role = authentication.getAuthorities().stream()
            .map(authority -> authority.getAuthority())
            .findFirst()
            .orElse("UNKNOWN");

    return "{\"role\": \"" + role + "\"}";
}

    @GetMapping("/test1")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String check() {
        return "passed~!1";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('USER')")
    public String checks() {
        return "passed~!2";
    }

    @GetMapping("/test3")
    @PreAuthorize("hasAuthority('USER')")
    public String checkss() {
        return "passed~!3";
    }


}
