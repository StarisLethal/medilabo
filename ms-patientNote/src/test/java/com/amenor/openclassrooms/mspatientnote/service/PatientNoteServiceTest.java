package com.amenor.openclassrooms.mspatientnote.service;

import com.amenor.openclassrooms.mspatientnote.config.SymptomDictionary;
import com.amenor.openclassrooms.mspatientnote.model.PatientNote;
import com.amenor.openclassrooms.mspatientnote.repository.PatientNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientNoteServiceTest {

    @Mock
    private PatientNoteRepository patientNoteRepository;

    @Mock
    private SymptomDictionary symptomDictionary;

    @InjectMocks
    private PatientNoteService patientNoteService;

    private UUID patientNote1Id;
    private UUID patientNote2Id;
    private UUID patientNote3Id;
    private UUID patientNote4Id;
    private UUID patient1Id;
    private UUID patient2Id;
    private String patientNoteContent1;
    private String patientNoteContent2;
    private String patientNoteContent3;
    private String patientNoteContent4;
    private PatientNote patientNote1;
    private PatientNote patientNote2;
    private PatientNote patientNote3;
    private PatientNote patientNote4;
    private Map<String, String> symptoms;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patientNote1Id = UUID.randomUUID();
        patientNote2Id = UUID.randomUUID();
        patientNote3Id = UUID.randomUUID();
        patientNote4Id = UUID.randomUUID();
        patient1Id = UUID.randomUUID();
        patient2Id = UUID.randomUUID();
        patientNoteContent1 = "Patient 1 Note 1";
        patientNoteContent2 = "Patient 1 Note 2";
        patientNoteContent3 = "Patient 2 Note 1";
        patientNoteContent4 = "Patient 2 Note 2";

        patientNote1 = new PatientNote(patientNote1Id, patient1Id, patientNoteContent1);
        patientNote2 = new PatientNote(patientNote2Id, patient1Id, patientNoteContent2);
        patientNote3 = new PatientNote(patientNote3Id, patient2Id, patientNoteContent3);
        patientNote4 = new PatientNote(patientNote4Id, patient2Id, patientNoteContent4);

        symptoms = Map.of("a", "1", "b", "2", "c","3", "d", "4", "e", "5", "f", "6", "g", "7", "h", "8");
    }



    @Test
    void getAllPatientNotes() {

        List<PatientNote> patientNotes = List.of(patientNote1, patientNote2, patientNote3, patientNote4);

        when(patientNoteRepository.findAll()).thenReturn(patientNotes);

        List<PatientNote> actual = patientNoteService.getAllPatientNotes();

        assertEquals(patientNotes.size(), actual.size());
        assertEquals(patientNoteContent1, actual.get(0).getPatientNote());
        assertEquals(patientNoteContent2, actual.get(1).getPatientNote());
        assertEquals(patientNoteContent3, actual.get(2).getPatientNote());
        assertEquals(patientNoteContent4, actual.get(3).getPatientNote());
        verify(patientNoteRepository, times(1)).findAll();

    }

    @Test
    void getPatientNotebyPatientId() {

        List<PatientNote> patientNotes = List.of(patientNote1, patientNote2);

        when(patientNoteRepository.findByPatientId(patient1Id)).thenReturn(patientNotes);

        List<PatientNote> actual = patientNoteService.getPatientNotebyPatientId(patient1Id);

        assertEquals(patientNotes.size(), actual.size());
        assertEquals(patientNoteContent1, actual.get(0).getPatientNote());
        assertEquals(patientNoteContent2, actual.get(1).getPatientNote());
        verify(patientNoteRepository, times(1)).findByPatientId(patient1Id);

    }

    @Test
    void getPatientNoteByID() {

        when(patientNoteRepository.findById(patientNote1Id)).thenReturn(Optional.of(patientNote1));

        Optional<PatientNote> actual = patientNoteService.getPatientNoteByID(patientNote1Id);

        assertTrue(actual.isPresent());
        assertEquals(patientNoteContent1, actual.get().getPatientNote());
        verify(patientNoteRepository, times(1)).findById(patientNote1Id);

    }

    @Test
    void createPatientNote() {

        when(patientNoteRepository.save(any(PatientNote.class))).thenReturn(patientNote1);

        PatientNote actual = patientNoteService.createPatientNote(patientNote1);

        assertNotNull(actual);
        assertEquals(patientNoteContent1, actual.getPatientNote());
        verify(patientNoteRepository, times(1)).save(any(PatientNote.class));

    }

    @Test
    void symptomCount() {

        List<String> notes = List.of("1", "2");

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        Integer count = patientNoteService.symptomCount(patient1Id);

        assertEquals(2, count);
        verify(patientNoteRepository, times(1)).findPatientNoteByPatientId(patient1Id);

    }

    @Test
    void diabeteEarlyOnSet() {
        String birthdate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String gender = "M";

        List<String> notes = List.of("1", "2", "3", "4", "5", "6");

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        Boolean result = patientNoteService.diabeteEarlyOnSet(birthdate, gender, patient1Id);

        assertTrue(result);
    }

    @Test
    void diabeteDanger() {

        String birthdate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String gender = "M";

        List<String> notes = List.of("1", "2", "3");

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        Boolean result = patientNoteService.diabeteDanger(birthdate, gender, patient1Id);

        assertTrue(result);

    }

    @Test
    void diabeteBorderLine() {

        String birthdate = LocalDate.now().minusYears(35).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        List<String> notes = List.of("1", "2", "3", "4");

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        Boolean result = patientNoteService.diabeteBorderLine(birthdate, patient1Id);

        assertTrue(result);

    }

    @Test
    void diabeteNone() {

        List<String> notes = List.of();

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        Boolean result = patientNoteService.diabeteNone(patient1Id);

        assertTrue(result);

    }

    @Test
    void diabeteDiagnose() {

        String birthdate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String gender = "M";
        List<String> notes = List.of("1");

        when(patientNoteRepository.findPatientNoteByPatientId(patient1Id)).thenReturn(notes);
        when(symptomDictionary.getDictionary()).thenReturn(symptoms);

        String result = patientNoteService.diabeteDiagnose(birthdate, gender,patient1Id);

        assertEquals("Not enough data", result);

    }

    @Test
    void getAge() {

        String birthdate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Integer age = patientNoteService.getAge(birthdate);

        assertNotNull(age);
        assertEquals(25, age);

    }
}