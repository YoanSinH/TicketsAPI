package com.example.crappBackend.repository;

import com.example.crappBackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Iterable<Ticket> findByStatus(final String status);
    Iterable<Ticket> findByEmail(final String email);
    Iterable<Ticket> findByPicked(final boolean picked);
}
