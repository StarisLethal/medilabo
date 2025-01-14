package com.amenor.openclassrooms.mspatientnote.service;

import com.amenor.openclassrooms.mspatientnote.config.SymptomDictionary;
import com.amenor.openclassrooms.mspatientnote.model.PatientNote;
import com.amenor.openclassrooms.mspatientnote.repository.PatientNoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PatientNoteService {

    final private PatientNoteRepository patientNoteRepository;
    final private SymptomDictionary symptomDictionary;

    public PatientNoteService(PatientNoteRepository patientNoteRepository, SymptomDictionary symptomDictionary) {
        this.patientNoteRepository = patientNoteRepository;
        this.symptomDictionary = symptomDictionary;

    }

    public List<PatientNote> getAllPatientNotes() {
        return patientNoteRepository.findAll();
    }

    public List<PatientNote> getPatientNotebyPatientId(UUID patientId) {
        return patientNoteRepository.findByPatientId(patientId);
    }

    public Optional<PatientNote> getPatientNoteByID(UUID PatientNoteId) {
        return patientNoteRepository.findById(PatientNoteId);
    }

    public PatientNote createPatientNote(PatientNote patientNote) {
        patientNote.setPatientNoteId(UUID.randomUUID());
        return patientNoteRepository.save(patientNote);
    }

    public Integer symptomCount(UUID id) {
        List<String> list = patientNoteRepository.findPatientNoteByPatientId(id);

        List<String> noteToCount = list.stream()
                .map(note -> note.replaceAll("\\p{Punct}", "").toLowerCase())
                .toList();

        Integer symptomCount = 0;
        for (Map.Entry<String, String> entry : symptomDictionary.getDictionary().entrySet()) {
            Pattern pattern = Pattern.compile(entry.getValue());
            for (String note : noteToCount) {
                if (pattern.matcher(note).find()) {
                    symptomCount++;

                    break;
                }
            }
        }
        return symptomCount;
    }


    public Boolean diabeteEarlyOnSet(String birthDate, String gender, UUID id) {
        Integer symptom = symptomCount(id);
        Integer age = getAge(birthDate);
        if ((gender.equals("M") && age < 30 && symptom > 5) || (gender.equals("F") && age < 30 && symptom > 7) || (age >= 30 && symptom > 8)) {

            return true;
        }
        return false;
    }

    public Boolean diabeteDanger(String birthDate, String gender, UUID id) {
        Integer symptom = symptomCount(id);
        Integer age = getAge(birthDate);
        if ((gender.equals("M") && age < 30 && symptom == 3) || (gender.equals("F") && age < 30 && symptom == 4) || (age >= 30 && symptom == 6 || symptom == 7)) {

            return true;
        }
        return false;
    }

    public Boolean diabeteBorderLine(String birthDate, UUID id) {
        Integer symptom = symptomCount(id);
        Integer age = getAge(birthDate);
        if ((age >= 30) && (symptom >= 2 && symptom <= 5)) {

            return true;
        }
        return false;
    }

    public Boolean diabeteNone(UUID id) {
        if (symptomCount(id) == 0) {
            return true;
        }

        return false;
    }

    public String diabeteDiagnose(String birthDate, String gender, UUID id) {
        if (diabeteEarlyOnSet(birthDate, gender, id)) {
            return "Early onset";
        }
        if (diabeteDanger(birthDate, gender, id)) {
            return "In Danger";
        }
        if (diabeteBorderLine(birthDate, id)) {
            return "Borderline";
        }
        if (diabeteNone(id)) {
            return "None";
        } else {
            return "Not enough data";
        }
    }

    public Integer getAge(String birthDate) {
        LocalDate birthday = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }
}
