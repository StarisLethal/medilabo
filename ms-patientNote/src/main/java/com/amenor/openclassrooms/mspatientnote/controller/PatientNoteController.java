package com.amenor.openclassrooms.mspatientnote.controller;

import com.amenor.openclassrooms.mspatientnote.model.PatientNote;
import com.amenor.openclassrooms.mspatientnote.service.PatientNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/patientNotes")
public class PatientNoteController {

    private static final Logger logger = Logger.getLogger(PatientNoteController.class.getName());

    @Autowired
    private PatientNoteService patientNoteService;

    @GetMapping("")
    public ResponseEntity<List<PatientNote>> getPatientNotes() {
        try {
            logger.info("getPatientNotes called");
            List<PatientNote> patientNotes = patientNoteService.getAllPatientNotes();
            return ResponseEntity.ok(patientNotes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "getPatientNotes failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<PatientNote>> getPatientNoteByPatientId(@PathVariable("patientId") UUID patientId) {
        try {
            logger.info("getPatientNoteById called");
            List<PatientNote> patientNotes = patientNoteService.getPatientNotebyPatientId(patientId);
            return ResponseEntity.ok(patientNotes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "getPatientNoteById failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/byNote/{patientNoteId}")
    public ResponseEntity<Optional<PatientNote>> getPatientNoteById(@PathVariable("patientNoteId") UUID patientNoteId) {
        try {
            logger.info("getPatientNoteByPatientId called");
            Optional<PatientNote> patientNotes = patientNoteService.getPatientNoteByID(patientNoteId);
            return ResponseEntity.ok(patientNotes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "getPatientNoteByPatientId failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<PatientNote> createPatientNote(@RequestBody PatientNote patientNote) {
        try {
            logger.info("createPatientNote called");
            PatientNote newpatientNote = patientNoteService.createPatientNote(patientNote);
            return ResponseEntity.status(HttpStatus.CREATED).body(newpatientNote);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "createPatientNote failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/diagnose")
    public ResponseEntity<String> getDiabeteDiagnose(@RequestParam("patientId") UUID patientId,
                                                     @RequestParam("gender") String gender,
                                                     @RequestParam("birthDate") String birthDate) {
        try {
            logger.info("getDiabeteDignose called");
            String diagnose = patientNoteService.diabeteDiagnose(birthDate, gender, patientId);
            return ResponseEntity.ok(diagnose);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "getDiabeteDignose failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
