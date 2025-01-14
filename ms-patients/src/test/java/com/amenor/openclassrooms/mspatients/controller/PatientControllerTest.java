package com.amenor.openclassrooms.mspatients.controller;

import com.amenor.openclassrooms.mspatients.model.Patient;
import com.amenor.openclassrooms.mspatients.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {


    @MockitoBean
    private PatientService patientService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void listPatients() throws Exception {

        Patient patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setFirstName("John");
        patient.setLastName("Doe");

        Patient patient2 = new Patient();
        patient2.setId(UUID.randomUUID());
        patient2.setFirstName("Jane");
        patient2.setLastName("Doe");

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient, patient2));


        mockMvc.perform(get("/api/v1/patients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(patient.getId().toString()))
                .andExpect(jsonPath("$[0].firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(patient.getLastName()))
                .andExpect(jsonPath("$[1].id").value(patient2.getId().toString()))
                .andExpect(jsonPath("$[1].firstName").value(patient2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(patient2.getLastName()));
    }

    @Test
    void getPatientById() throws Exception {
        UUID id = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientService.getPatientById(id)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/v1/patients/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()));
    }

    @Test
    void createPatient() throws Exception {
        UUID id = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/v1/patients")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()));
    }

    @Test
    void updatePatient() throws Exception {
        UUID id = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        Patient patient2 = new Patient();
        patient2.setFirstName("Jane");
        patient2.setLastName("Doe");

        when(patientService.updatePatient(any(Patient.class), any(UUID.class))).thenReturn(true);

        mockMvc.perform(put("/api/v1/patients/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\": \"Jane\", \"lastName\": \"Doe\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}