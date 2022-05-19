package com.example.crappBackend.Controller;

import com.example.crappBackend.model.Admin;
import com.example.crappBackend.repository.AdminRepository;
import com.example.crappBackend.useCase.ResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
<<<<<<< HEAD
=======



>>>>>>> 89cb9d3 (CD Implemented)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id){
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Admin con la siguiente id no existe" + id));
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin){
        return adminRepository.save(admin);
    }

    @PutMapping("{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails){
        Admin updateAdmin = adminRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundExeption("Admin con la siguiente id no existe: " + id));

        updateAdmin.setFirstName(adminDetails.getFirstName());
        updateAdmin.setLastName(adminDetails.getLastName());
        updateAdmin.setEmail(adminDetails.getEmail());
        updateAdmin.setPassword(adminDetails.getPassword());

        adminRepository.save(updateAdmin);

        return ResponseEntity.ok(updateAdmin);
    }

    @DeleteMapping("{id}")
    public void deleteAdminById(@PathVariable Long id){
        adminRepository.deleteById(id);
    }
}
