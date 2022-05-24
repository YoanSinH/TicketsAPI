package com.example.crappBackend.Controller;

import com.example.crappBackend.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public ResponseEntity<String> get(){
        String response = "Welcome to CRAPP API";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
