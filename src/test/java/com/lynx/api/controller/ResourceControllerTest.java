package com.lynx.api.controller;

import com.lynx.api.model.Resource;
import com.lynx.api.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        resourceService.getAllResources().forEach(r -> resourceService.deleteResource(r.getId()));
    }

    @Test
    void shouldCreateResource() throws Exception {
        String resourceJson = "{\"name\":\"Test Resource\",\"description\":\"Test Description\"}";

        mockMvc.perform(post("/api/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resourceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Resource"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    void shouldGetAllResources() throws Exception {
        Resource resource = new Resource();
        resource.setName("Test Resource");
        resource.setDescription("Test Description");
        resourceService.createResource(resource);

        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Resource"));
    }

    @Test
    void shouldGetResourceById() throws Exception {
        Resource resource = new Resource();
        resource.setName("Test Resource");
        resource.setDescription("Test Description");
        Resource created = resourceService.createResource(resource);

        mockMvc.perform(get("/api/resources/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("Test Resource"));
    }

    @Test
    void shouldUpdateResource() throws Exception {
        Resource resource = new Resource();
        resource.setName("Original Name");
        resource.setDescription("Original Description");
        Resource created = resourceService.createResource(resource);

        String updatedJson = "{\"name\":\"Updated Name\",\"description\":\"Updated Description\",\"status\":\"inactive\"}";

        mockMvc.perform(put("/api/resources/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("inactive"));
    }

    @Test
    void shouldDeleteResource() throws Exception {
        Resource resource = new Resource();
        resource.setName("To Delete");
        resource.setDescription("This will be deleted");
        Resource created = resourceService.createResource(resource);

        mockMvc.perform(delete("/api/resources/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/resources/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404ForNonExistentResource() throws Exception {
        mockMvc.perform(get("/api/resources/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldValidateResourceName() throws Exception {
        String invalidResourceJson = "{\"description\":\"No name provided\"}";

        mockMvc.perform(post("/api/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidResourceJson))
                .andExpect(status().isBadRequest());
    }
}
