package com.amenor.openclassrooms.mspatientnote.controller;

import com.amenor.openclassrooms.mspatientnote.model.PatientNote;
import com.amenor.openclassrooms.mspatientnote.service.PatientNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientNoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientNoteService patientNoteService;

    @InjectMocks
    private PatientNoteController patientNoteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       this.mockMvc = MockMvcBuilders.standaloneSetup(patientNoteController).build();
    }


    @Test
    void getPatientNotes() throws Exception {

        List<PatientNote> patientNotes = List.of(new PatientNote(UUID.randomUUID(), UUID.randomUUID(), "1"),  new PatientNote(UUID.randomUUID(), UUID.randomUUID(), "2"));


        when(patientNoteService.getAllPatientNotes()).thenReturn(patientNotes);


        mockMvc.perform(get("/api/v1/patientNotes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(patientNotes.size()))
                .andExpect(jsonPath("$[0].patientNote").value(patientNotes.get(0).getPatientNote()))
                .andExpect(jsonPath("$[1].patientNote").value(patientNotes.get(1).getPatientNote()));

        verify(patientNoteService).getAllPatientNotes();

    }

    @Test
    void getPatientNoteByPatientId() throws Exception {

        UUID patientId = UUID.randomUUID();
        List<PatientNote> patientNotes = List.of(new PatientNote(UUID.randomUUID(), patientId, "1"),  new PatientNote(UUID.randomUUID(), patientId, "2"));


        when(patientNoteService.getPatientNotebyPatientId(patientId)).thenReturn(patientNotes);

        mockMvc.perform(get("/api/v1/patientNotes/{patientId}", patientId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(patientNotes.size()))
                .andExpect(jsonPath("$[0].patientNote").value(patientNotes.get(0).getPatientNote()))
                .andExpect(jsonPath("$[1].patientNote").value(patientNotes.get(1).getPatientNote()));

        verify(patientNoteService).getPatientNotebyPatientId(patientId);

    }

    @Test
    void getPatientNoteById() throws Exception {

        UUID noteId = UUID.randomUUID();
        PatientNote patientNote = new PatientNote(noteId, UUID.randomUUID(),  "1");
        when(patientNoteService.getPatientNoteByID(noteId)).thenReturn(Optional.of(patientNote));


        mockMvc.perform(get("/api/v1/patientNotes/byNote/{patientNoteId}", noteId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientNote").value(patientNote.getPatientNote()));

        verify(patientNoteService).getPatientNoteByID(noteId);

    }

    @Test
    void createPatientNote() throws Exception {

        PatientNote patientNoteContent = new PatientNote(null, UUID.randomUUID(), "1");
        PatientNote patientNoteCreated = new PatientNote(UUID.randomUUID(), patientNoteContent.getPatientId(), patientNoteContent.getPatientNote());
        when(patientNoteService.createPatientNote(any(PatientNote.class))).thenReturn(patientNoteCreated);


        mockMvc.perform(post("/api/v1/patientNotes").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\":\"" + patientNoteContent.getPatientId() + "\",\"patientNote\":\"" + patientNoteContent.getPatientNote() + "\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientNote").value(patientNoteContent.getPatientNote()));


    }

    @Test
    void getDiabeteDiagnose() throws Exception {

        UUID patientId = UUID.randomUUID();
        String birthDate = "1980-01-01";
        String gender = "M";
        String diagnosis = "Early onset";
        when(patientNoteService.diabeteDiagnose(birthDate, gender, patientId)).thenReturn(diagnosis);

        mockMvc.perform(get("/api/v1/patientNotes/diagnose")
                        .param("patientId", patientId.toString())
                        .param("birthDate", birthDate)
                        .param("gender", gender)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(diagnosis));

        verify(patientNoteService, times(1)).diabeteDiagnose(birthDate, gender, patientId);

    }
}