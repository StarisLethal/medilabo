package com.amenor.openclassrooms.msfrontend.proxies;

import com.amenor.openclassrooms.msfrontend.bean.PatientBean;
import com.amenor.openclassrooms.msfrontend.bean.PatientNoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;


@FeignClient(name = "MS-GATEWAY")
public interface PatientNoteProxy {
    @GetMapping("/patientNotes/{patientId}")
    List<PatientNoteBean> getPatientNoteByPatientId(@PathVariable("patientId") UUID patientId);

    @GetMapping("/patientNotes/byNote/{patientNoteId}")
    PatientNoteBean getPatientNoteByNoteId(@PathVariable("patientNoteId") UUID noteId);

    @PostMapping("/patientNotes")
    PatientNoteBean createPatientNote(@RequestBody PatientNoteBean patientNoteBean);
}
