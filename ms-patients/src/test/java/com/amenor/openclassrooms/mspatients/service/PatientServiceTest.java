package com.amenor.openclassrooms.mspatients.service;

import com.amenor.openclassrooms.mspatients.model.Patient;
import com.amenor.openclassrooms.mspatients.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    PatientServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients() {

        Patient patient1 = new Patient();
        patient1.setFirstName("John");
        patient1.setLastName("Doe");

        Patient patient2 = new Patient();
        patient2.setFirstName("Jane");
        patient2.setLastName("Smith");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);


        List<Patient> result = patientService.getAllPatients();


        assertEquals(patients, result);
        assertEquals(patient1, result.get(0));
        assertEquals(patient2, result.get(1));
    }

    @Test
    void getPatientById() {

        UUID id = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));


        Optional<Patient> result = patientService.getPatientById(id);


        assertTrue(result.isPresent(), "Patient found");
        assertEquals(patient, result.get(), "Patient = Result");
    }

    @Test
    void addPatient() {

        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientRepository.save(patient)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        Patient result = patientService.addPatient(patient);


        assertEquals(patient, result , "Patient added");
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void updatePatient() {
        UUID id = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        Patient updatedPatient = new Patient();
        updatedPatient.setId(UUID.randomUUID());
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Smith");

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        Boolean result = patientService.updatePatient(updatedPatient, id);

        assertTrue(result);
        assertEquals(id, patient.getId());
        assertEquals("Jane", patient.getFirstName());
        assertEquals("Smith", patient.getLastName());
        verify(patientRepository, times(1)).save(patient);
    }
}