package com.amenor.openclassrooms.mspatientnote.controller;

import com.amenor.openclassrooms.mspatientnote.model.PatientNote;
import com.amenor.openclassrooms.mspatientnote.service.PatientNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patientNotes")
public class PatientNoteController {

    private static final Logger logger = LoggerFactory.getLogger(PatientNoteController.class);

    @Autowired
    private PatientNoteService patientNoteService;

    @GetMapping("")
    public ResponseEntity<List<PatientNote>> getPatientNotes() {
        logger.info("getPatientNotes called");
        try {
            List<PatientNote> patientNotes = patientNoteService.getAllPatientNotes();
            logger.info("getPatientNotes success, {} notes found", patientNotes.size());
            return ResponseEntity.ok(patientNotes);
        } catch (Exception e) {
            logger.error("getPatientNotes failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<PatientNote>> getPatientNoteByPatientId(@PathVariable("patientId") UUID patientId) {
        logger.info("getPatientNoteById called for patientId: {}", patientId);
        try {
            List<PatientNote> patientNotes = patientNoteService.getPatientNotebyPatientId(patientId);
            logger.info("getPatientNoteById success, {} notes found for patientId: {}", patientNotes.size(), patientId);
            return ResponseEntity.ok(patientNotes);
        } catch (Exception e) {
            logger.error("getPatientNoteById failed for patientId: {}", patientId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/byNote/{patientNoteId}")
    public ResponseEntity<Optional<PatientNote>> getPatientNoteById(@PathVariable("patientNoteId") UUID patientNoteId) {
        logger.info("getPatientNoteById called for patientNoteId: {}", patientNoteId);
        try {
            Optional<PatientNote> patientNote = patientNoteService.getPatientNoteByID(patientNoteId);
            logger.info("getPatientNoteById success for patientNoteId: {}", patientNoteId);
            return ResponseEntity.ok(patientNote);
        } catch (Exception e) {
            logger.error("getPatientNoteById failed for patientNoteId: {}", patientNoteId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<PatientNote> createPatientNote(@RequestBody PatientNote patientNote) {
        logger.info("createPatientNote called for patientNote: {}", patientNote);
        try {
            PatientNote newPatientNote = patientNoteService.createPatientNote(patientNote);
            logger.info("createPatientNote success, created note: {}", newPatientNote);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPatientNote);
        } catch (Exception e) {
            logger.error("createPatientNote failed for patientNote: {}", patientNote, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/diagnose")
    public ResponseEntity<String> getDiabeteDiagnose(@RequestParam("patientId") UUID patientId,
                                                     @RequestParam("gender") String gender,
                                                     @RequestParam("birthDate") String birthDate) {
        logger.info("getDiabeteDiagnose called with patientId: {}, birthDate: {}, gender: {}", patientId, birthDate, gender);
        try {
            String diagnose = patientNoteService.diabeteDiagnose(birthDate, gender, patientId);
            logger.info("getDiabeteDiagnose success for patientId: {}", patientId);
            return ResponseEntity.ok(diagnose);
        } catch (Exception e) {
            logger.error("getDiabeteDiagnose failed for patientId: {}", patientId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
