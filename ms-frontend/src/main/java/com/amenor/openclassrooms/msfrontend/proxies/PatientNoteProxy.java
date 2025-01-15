package com.amenor.openclassrooms.msfrontend.proxies;

import com.amenor.openclassrooms.msfrontend.bean.PatientNoteBean;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@FeignClient(name = "MS-GATEWAY", contextId = "patientNoteProxy")
public interface PatientNoteProxy {
    @GetMapping("/patientNotes/{patientId}")
    List<PatientNoteBean> getPatientNoteByPatientId(@PathVariable("patientId") UUID patientId);

    @GetMapping("/patientNotes/byNote/{patientNoteId}")
    PatientNoteBean getPatientNoteByNoteId(@PathVariable("patientNoteId") UUID noteId);

    @PostMapping("/patientNotes")
    PatientNoteBean createPatientNote(@RequestBody PatientNoteBean patientNoteBean);

    @GetMapping("/patientNotes/diagnose")
    String getDiabeteDiagnose(@RequestParam("patientId") UUID patientId,
                              @RequestParam("birthDate") String birthDate,
                              @RequestParam("gender") String gender);
}
