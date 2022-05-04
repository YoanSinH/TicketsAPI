package com.example.crappBackend.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.crappBackend.model.Collector;
import com.example.crappBackend.repository.CollectrorRepository;
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

@ContextConfiguration(classes = {CollectorController.class})
@ExtendWith(SpringExtension.class)
class CollectorControllerTest {
    @Autowired
    private CollectorController collectorController;

    @MockBean
    private CollectrorRepository collectrorRepository;

    /**
     * Method under test: {@link CollectorController#createCollector(Collector)}
     */
    @Test
    void testCreateCollector() throws Exception {
        when(this.collectrorRepository.findAll()).thenReturn(new ArrayList<>());

        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(collector);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/collector")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CollectorController#createCollector(Collector)}
     */
    @Test
    void testCreateCollector2() throws Exception {
        when(this.collectrorRepository.findAll()).thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));

        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(collector);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/collector")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CollectorController#deleteCollector(Long)}
     */
    @Test
    void testDeleteCollector() throws Exception {
        doNothing().when(this.collectrorRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/collector/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CollectorController#deleteCollector(Long)}
     */
    @Test
    void testDeleteCollector2() throws Exception {
        doNothing().when(this.collectrorRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/collector/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CollectorController#deleteCollector(Long)}
     */
    @Test
    void testDeleteCollector3() throws Exception {
        doThrow(new ResourceNotFoundExeption("Not all who wander are lost")).when(this.collectrorRepository)
                .deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/collector/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CollectorController#getCollectorById(Long)}
     */
    @Test
    void testGetCollectorById() throws Exception {
        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        Optional<Collector> ofResult = Optional.of(collector);
        when(this.collectrorRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/collector/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link CollectorController#getCollectorById(Long)}
     */
    @Test
    void testGetCollectorById2() throws Exception {
        when(this.collectrorRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/collector/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CollectorController#getCollectorById(Long)}
     */
    @Test
    void testGetCollectorById3() throws Exception {
        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        Optional<Collector> ofResult = Optional.of(collector);
        when(this.collectrorRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/collector/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link CollectorController#getCollectorById(Long)}
     */
    @Test
    void testGetCollectorById4() throws Exception {
        when(this.collectrorRepository.findById((Long) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/collector/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CollectorController#updateCollector(Long, Collector)}
     */
    @Test
    void testUpdateCollector() throws Exception {
        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        Optional<Collector> ofResult = Optional.of(collector);

        Collector collector1 = new Collector();
        collector1.setEmail("jane.doe@example.org");
        collector1.setFirstName("Jane");
        collector1.setId(123L);
        collector1.setLastName("Doe");
        collector1.setPassword("iloveyou");
        when(this.collectrorRepository.save((Collector) any())).thenReturn(collector1);
        when(this.collectrorRepository.findById((Long) any())).thenReturn(ofResult);

        Collector collector2 = new Collector();
        collector2.setEmail("jane.doe@example.org");
        collector2.setFirstName("Jane");
        collector2.setId(123L);
        collector2.setLastName("Doe");
        collector2.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(collector2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/collector/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link CollectorController#updateCollector(Long, Collector)}
     */
    @Test
    void testUpdateCollector2() throws Exception {
        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        Optional<Collector> ofResult = Optional.of(collector);
        when(this.collectrorRepository.save((Collector) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        when(this.collectrorRepository.findById((Long) any())).thenReturn(ofResult);

        Collector collector1 = new Collector();
        collector1.setEmail("jane.doe@example.org");
        collector1.setFirstName("Jane");
        collector1.setId(123L);
        collector1.setLastName("Doe");
        collector1.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(collector1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/collector/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CollectorController#updateCollector(Long, Collector)}
     */
    @Test
    void testUpdateCollector3() throws Exception {
        Collector collector = new Collector();
        collector.setEmail("jane.doe@example.org");
        collector.setFirstName("Jane");
        collector.setId(123L);
        collector.setLastName("Doe");
        collector.setPassword("iloveyou");
        when(this.collectrorRepository.save((Collector) any())).thenReturn(collector);
        when(this.collectrorRepository.findById((Long) any())).thenReturn(Optional.empty());

        Collector collector1 = new Collector();
        collector1.setEmail("jane.doe@example.org");
        collector1.setFirstName("Jane");
        collector1.setId(123L);
        collector1.setLastName("Doe");
        collector1.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(collector1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/collector/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.collectorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

