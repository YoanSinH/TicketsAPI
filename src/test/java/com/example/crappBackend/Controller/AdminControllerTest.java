package com.example.crappBackend.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.crappBackend.model.Admin;
import com.example.crappBackend.repository.AdminRepository;
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

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private AdminRepository adminRepository;

    /**
     * Method under test: {@link AdminController#createAdmin(Admin)}
     */
    @Test
    void testCreateAdmin() throws Exception {
        when(this.adminRepository.findAll()).thenReturn(new ArrayList<>());

        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(admin);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AdminController#createAdmin(Admin)}
     */
    @Test
    void testCreateAdmin2() throws Exception {
        when(this.adminRepository.findAll()).thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));

        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(admin);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#deleteAdminById(Long)}
     */
    @Test
    void testDeleteAdminById() throws Exception {
        doNothing().when(this.adminRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/admin/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AdminController#deleteAdminById(Long)}
     */
    @Test
    void testDeleteAdminById2() throws Exception {
        doNothing().when(this.adminRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/admin/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AdminController#deleteAdminById(Long)}
     */
    @Test
    void testDeleteAdminById3() throws Exception {
        doThrow(new ResourceNotFoundExeption("Not all who wander are lost")).when(this.adminRepository)
                .deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/admin/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#getAdminById(Long)}
     */
    @Test
    void testGetAdminById() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        Optional<Admin> ofResult = Optional.of(admin);
        when(this.adminRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link AdminController#getAdminById(Long)}
     */
    @Test
    void testGetAdminById2() throws Exception {
        when(this.adminRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#getAdminById(Long)}
     */
    @Test
    void testGetAdminById3() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        Optional<Admin> ofResult = Optional.of(admin);
        when(this.adminRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/admin/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link AdminController#getAdminById(Long)}
     */
    @Test
    void testGetAdminById4() throws Exception {
        when(this.adminRepository.findById((Long) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#getAllAdmin()}
     */
    @Test
    void testGetAllAdmin() throws Exception {
        when(this.adminRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin");
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AdminController#getAllAdmin()}
     */
    @Test
    void testGetAllAdmin2() throws Exception {
        when(this.adminRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/admin");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AdminController#getAllAdmin()}
     */
    @Test
    void testGetAllAdmin3() throws Exception {
        when(this.adminRepository.findAll()).thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#updateAdmin(Long, Admin)}
     */
    @Test
    void testUpdateAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        Optional<Admin> ofResult = Optional.of(admin);

        Admin admin1 = new Admin();
        admin1.setEmail("jane.doe@example.org");
        admin1.setFirstName("Jane");
        admin1.setId(123L);
        admin1.setLastName("Doe");
        admin1.setPassword("iloveyou");
        when(this.adminRepository.save((Admin) any())).thenReturn(admin1);
        when(this.adminRepository.findById((Long) any())).thenReturn(ofResult);

        Admin admin2 = new Admin();
        admin2.setEmail("jane.doe@example.org");
        admin2.setFirstName("Jane");
        admin2.setId(123L);
        admin2.setLastName("Doe");
        admin2.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(admin2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/admin/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link AdminController#updateAdmin(Long, Admin)}
     */
    @Test
    void testUpdateAdmin2() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        Optional<Admin> ofResult = Optional.of(admin);
        when(this.adminRepository.save((Admin) any()))
                .thenThrow(new ResourceNotFoundExeption("Not all who wander are lost"));
        when(this.adminRepository.findById((Long) any())).thenReturn(ofResult);

        Admin admin1 = new Admin();
        admin1.setEmail("jane.doe@example.org");
        admin1.setFirstName("Jane");
        admin1.setId(123L);
        admin1.setLastName("Doe");
        admin1.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(admin1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/admin/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdminController#updateAdmin(Long, Admin)}
     */
    @Test
    void testUpdateAdmin3() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("jane.doe@example.org");
        admin.setFirstName("Jane");
        admin.setId(123L);
        admin.setLastName("Doe");
        admin.setPassword("iloveyou");
        when(this.adminRepository.save((Admin) any())).thenReturn(admin);
        when(this.adminRepository.findById((Long) any())).thenReturn(Optional.empty());

        Admin admin1 = new Admin();
        admin1.setEmail("jane.doe@example.org");
        admin1.setFirstName("Jane");
        admin1.setId(123L);
        admin1.setLastName("Doe");
        admin1.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(admin1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/admin/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

