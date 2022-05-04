package com.example.crappBackend.Controller;

import com.example.crappBackend.model.Ticket;
import com.example.crappBackend.repository.TicketRepository;
import com.example.crappBackend.useCase.ResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("/api/ticket")

public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getAllTicket(){ return ticketRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Ticket con la siguiente id no existe: " + id));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketRepository.save(ticket);
    }

    @PutMapping("{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails){
        Ticket updateTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Ticket con la siguiente id no existe: " + id));

        updateTicket.setEmail(ticketDetails.getEmail());
        updateTicket.setDirection(ticketDetails.getDirection());
        updateTicket.setDescription(ticketDetails.getDescription());
        updateTicket.setUrl(ticketDetails.getUrl());
        updateTicket.setPicked(ticketDetails.getPicked());

        ticketRepository.save(updateTicket);

        return ResponseEntity.ok(updateTicket);
    }

    @DeleteMapping("{id}")
    public  void deleteTicketById(@PathVariable Long id){
        ticketRepository.deleteById(id);
    }
}
