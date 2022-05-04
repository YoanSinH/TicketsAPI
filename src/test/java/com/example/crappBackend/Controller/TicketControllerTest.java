package com.example.crappBackend.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.crappBackend.model.Ticket;
import com.example.crappBackend.repository.TicketRepository;
import com.example.crappBackend.useCase.ResourceNotFoundExeption;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TicketController.class})
@ExtendWith(SpringExtension.class)
class TicketControllerTest {
    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketRepository ticketRepository;

    /**
     * Method under test: {@link TicketController#createTicket(Ticket)}
     */
    @Test
    void testCreateTicket() throws Exception {
        when(this.ticketRepository.findAll()).thenReturn(new ArrayList<>());

        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(ticket);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TicketController#createTicket(Ticket)}
     */
    @Test
    void testCreateTicket2() throws Exception {
        when(this.ticketRepository.findAll()).thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));

        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(ticket);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#deleteTicketById(Long)}
     */
    @Test
    void testDeleteTicketById() throws Exception {
        doNothing().when(this.ticketRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ticket/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link TicketController#deleteTicketById(Long)}
     */
    @Test
    void testDeleteTicketById2() throws Exception {
        doNothing().when(this.ticketRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/ticket/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link TicketController#deleteTicketById(Long)}
     */
    @Test
    void testDeleteTicketById3() throws Exception {
        doThrow(new ResourceNotFoundExeption("Not all who wander are lost")).when(this.ticketRepository)
                .deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ticket/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#getAllTicket()}
     */
    @Test
    void testGetAllTicket() throws Exception {
        when(this.ticketRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket");
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TicketController#getAllTicket()}
     */
    @Test
    void testGetAllTicket2() throws Exception {
        when(this.ticketRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/ticket");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TicketController#getAllTicket()}
     */
    @Test
    void testGetAllTicket3() throws Exception {
        when(this.ticketRepository.findAll()).thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#getTicketById(Long)}
     */
    @Test
    void testGetTicketById() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(this.ticketRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"email\":\"jane.doe@example.org\",\"direction\":\"Direction\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"url\":\"https://example.org/example\",\"picked\":true}"));
    }

    /**
     * Method under test: {@link TicketController#getTicketById(Long)}
     */
    @Test
    void testGetTicketById2() throws Exception {
        when(this.ticketRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#getTicketById(Long)}
     */
    @Test
    void testGetTicketById3() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(this.ticketRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/ticket/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"email\":\"jane.doe@example.org\",\"direction\":\"Direction\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"url\":\"https://example.org/example\",\"picked\":true}"));
    }

    /**
     * Method under test: {@link TicketController#getTicketById(Long)}
     */
    @Test
    void testGetTicketById4() throws Exception {
        when(this.ticketRepository.findById((Long) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ticket/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#updateTicket(Long, Ticket)}
     */
    @Test
    void testUpdateTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        Optional<Ticket> ofResult = Optional.of(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setDescription("The characteristics of someone or something");
        ticket1.setDirection("Direction");
        ticket1.setEmail("jane.doe@example.org");
        ticket1.setId(123L);
        ticket1.setPicked(true);
        ticket1.setUrl("https://example.org/example");
        when(this.ticketRepository.save((Ticket) any())).thenReturn(ticket1);
        when(this.ticketRepository.findById((Long) any())).thenReturn(ofResult);

        Ticket ticket2 = new Ticket();
        ticket2.setDescription("The characteristics of someone or something");
        ticket2.setDirection("Direction");
        ticket2.setEmail("jane.doe@example.org");
        ticket2.setId(123L);
        ticket2.setPicked(true);
        ticket2.setUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(ticket2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ticket/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"email\":\"jane.doe@example.org\",\"direction\":\"Direction\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"url\":\"https://example.org/example\",\"picked\":true}"));
    }

    /**
     * Method under test: {@link TicketController#updateTicket(Long, Ticket)}
     */
    @Test
    void testUpdateTicket2() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(this.ticketRepository.save((Ticket) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        when(this.ticketRepository.findById((Long) any())).thenReturn(ofResult);

        Ticket ticket1 = new Ticket();
        ticket1.setDescription("The characteristics of someone or something");
        ticket1.setDirection("Direction");
        ticket1.setEmail("jane.doe@example.org");
        ticket1.setId(123L);
        ticket1.setPicked(true);
        ticket1.setUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(ticket1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ticket/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TicketController#updateTicket(Long, Ticket)}
     */
    @Test
    void testUpdateTicket3() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("The characteristics of someone or something");
        ticket.setDirection("Direction");
        ticket.setEmail("jane.doe@example.org");
        ticket.setId(123L);
        ticket.setPicked(true);
        ticket.setUrl("https://example.org/example");
        when(this.ticketRepository.save((Ticket) any())).thenReturn(ticket);
        when(this.ticketRepository.findById((Long) any())).thenReturn(Optional.empty());

        Ticket ticket1 = new Ticket();
        ticket1.setDescription("The characteristics of someone or something");
        ticket1.setDirection("Direction");
        ticket1.setEmail("jane.doe@example.org");
        ticket1.setId(123L);
        ticket1.setPicked(true);
        ticket1.setUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(ticket1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ticket/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

