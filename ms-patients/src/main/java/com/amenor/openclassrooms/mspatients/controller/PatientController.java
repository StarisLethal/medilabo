package com.amenor.openclassrooms.mspatients.controller;


import com.amenor.openclassrooms.mspatients.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amenor.openclassrooms.mspatients.service.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private static final Logger logger = Logger.getLogger(PatientController.class.getName());

    @Autowired
    private PatientService patientService;

    @GetMapping("")
    public ResponseEntity<List<Patient>> listPatients() {
        try {
            logger.info("listPatients called");
            List<Patient> patients = patientService.getAllPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "listPatients failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Patient>> getPatientById(@PathVariable UUID id) {
        try {
            logger.info("getPatientById called");
            Optional<Patient> patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        }catch (Exception e) {
            logger.log(Level.SEVERE, "getPatientById failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        try {
            logger.info("createPatient called");
            Patient createPatient = patientService.addPatient(patient);
            return new ResponseEntity<>(createPatient, HttpStatus.CREATED);
        }catch (Exception e){
            logger.log(Level.SEVERE, "createPatient failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id, @RequestBody Patient patient) {
        try {
            logger.info("updatePatient called");
            Boolean updatePatient = patientService.updatePatient(patient, id);
            if (updatePatient) {
                return new ResponseEntity<>(patient, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.log(Level.SEVERE, "updatePatient failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

/*    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id) {
        try {
            logger.info("deletePatient called");
            patientService.deletePatient(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        }catch (Exception e){
            logger.log(Level.SEVERE, "deletePatient failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}
