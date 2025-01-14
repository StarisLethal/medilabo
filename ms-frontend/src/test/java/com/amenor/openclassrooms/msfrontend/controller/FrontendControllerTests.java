package com.amenor.openclassrooms.msfrontend.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.amenor.openclassrooms.msfrontend.bean.*;
import com.amenor.openclassrooms.msfrontend.controller.*;
import com.amenor.openclassrooms.msfrontend.proxies.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class FrontendControllerTests {

    @Mock
    private PatientProxy patientProxy;
    @Mock
    private PatientNoteProxy patientNoteProxy;
    @InjectMocks
    private PatientNoteController patientNoteController;
    @InjectMocks
    private PatientController patientController;

    @Test
    void testHome() {
        List<PatientBean> mockPatients = List.of(new PatientBean(UUID.randomUUID(), "John", "Doe", new Date(), "M", "123 Street", "1234567890"));
        when(patientProxy.getPatients()).thenReturn(mockPatients);

        Model model = new ConcurrentModel();
        String viewName = patientController.home(model);

        assertEquals("home", viewName);
        assertTrue(model.containsAttribute("patients"));
        assertEquals(mockPatients, model.getAttribute("patients"));
        verify(patientProxy, times(1)).getPatients();
    }

    @Test
    void testCreatePatient() {
        PatientBean patient = new PatientBean(UUID.randomUUID(), "Jane", "Smith", new Date(), "F", "456 Avenue", "0987654321");

        String viewName = patientController.createPatient(patient);

        verify(patientProxy, times(1)).createPatient(patient);
        assertEquals("redirect:", viewName);
    }

    @Test
    void testPatientNotes() {
        UUID patientId = UUID.randomUUID();
        List<PatientNoteBean> notes = List.of(new PatientNoteBean(UUID.randomUUID(), patientId, "John Doe", "Sample note"));
        when(patientNoteProxy.getPatientNoteByPatientId(patientId)).thenReturn(notes);

        Model model = new ConcurrentModel();
        String viewName = patientNoteController.patientNotes(model, patientId);

        assertEquals("patientNotes", viewName);
        assertTrue(model.containsAttribute("patientNotes"));
        assertEquals(notes, model.getAttribute("patientNotes"));
        verify(patientNoteProxy, times(1)).getPatientNoteByPatientId(patientId);
    }

    @Test
    void testCreateNote() {
        PatientNoteBean note = new PatientNoteBean(UUID.randomUUID(), UUID.randomUUID(), "Jane Smith", "This is a note.");

        String viewName = patientNoteController.createNote(new ConcurrentModel(), note);

        verify(patientNoteProxy, times(1)).createPatientNote(note);
        assertTrue(viewName.startsWith("redirect:/patientNotes?id="));
    }

    // Proxy Tests
    @Test
    void testPatientProxyMethods() {
        UUID id = UUID.randomUUID();
        PatientBean patient = new PatientBean(id, "Test", "User", new Date(), "M", "Test Address", "12345");

        when(patientProxy.getPatientById(id)).thenReturn(patient);

        PatientBean fetchedPatient = patientProxy.getPatientById(id);
        assertEquals(patient, fetchedPatient);
        verify(patientProxy, times(1)).getPatientById(id);
    }

    @Test
    void testPatientNoteProxyMethods() {
        UUID noteId = UUID.randomUUID();
        PatientNoteBean note = new PatientNoteBean(noteId, UUID.randomUUID(), "Name", "Note content");

        when(patientNoteProxy.getPatientNoteByNoteId(noteId)).thenReturn(note);

        PatientNoteBean fetchedNote = patientNoteProxy.getPatientNoteByNoteId(noteId);
        assertEquals(note, fetchedNote);
        verify(patientNoteProxy, times(1)).getPatientNoteByNoteId(noteId);
    }
}
