package com.example.crappBackend.Controller;

import com.example.crappBackend.model.Ticket;
import com.example.crappBackend.repository.TicketRepository;
import com.example.crappBackend.useCase.ResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @GetMapping("{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Ticket con la siguiente id no existe: " + id));
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("filter/{status}")
    public ResponseEntity<List<Ticket>> getTicketByStatus(@PathVariable String status){
        List<Ticket> ticketList = new ArrayList<>();
        ticketRepository.findByStatus(status).forEach(ticket -> {
            ticketList.add(ticket);
        });
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }

    @GetMapping("user/{email}")
    public ResponseEntity<List<Ticket>> getTicketByEmail(@PathVariable String email){
        List<Ticket> ticketList = new ArrayList<>();
        ticketRepository.findByEmail(email).forEach(ticket -> {
            ticketList.add(ticket);
        });
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }

    @RequestMapping(value = "userhistory/{email}/{status}", method = RequestMethod.GET)
    @GetMapping("userhistory/{email}/{status}")
    public ResponseEntity<List<Ticket>> getTicketByParam(@PathVariable("email") String email, @PathVariable("status") String status){
        List<Ticket> ticketListParam = new ArrayList<>();
        ticketRepository.findByEmail(email).forEach(ticket -> {
            if(ticket.getStatus().equals(status) && ticket.getEmail().equals(email)) {
                ticketListParam.add(ticket);
            }
        });
        return new ResponseEntity<>(ticketListParam, HttpStatus.OK);
    }

    /* tbd
    @RequestMapping(value = "allhistory/{param}", method = RequestMethod.GET)
    @GetMapping("allhistory/{param}")
    public  ResponseEntity<List<Ticket>> getHistoryByParam(@PathVariable("param") String param){
        List<Ticket> ticketListHistory = new ArrayList<>();
        if(param.equals("true")){
            boolean paramb = Boolean.parseBoolean(param);
            ticketRepository.findByPicked(paramb).forEach(ticket -> {
                    ticketListHistory.add(ticket);
            });
        }
        return new ResponseEntity<>(ticketListHistory, HttpStatus.OK);
    }*/

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
        updateTicket.setStatus(ticketDetails.getStatus());

        ticketRepository.save(updateTicket);

        return ResponseEntity.ok(updateTicket);
    }

    @DeleteMapping("{id}")
    public  void deleteTicketById(@PathVariable Long id){
        ticketRepository.deleteById(id);
    }
}
