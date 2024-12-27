package com.amenor.openclassrooms.msfrontend.proxies;

import com.amenor.openclassrooms.msfrontend.bean.PatientBean;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MS-GATEWAY")
public interface PatientProxy {

    @GetMapping("/patients")
    List<PatientBean> getPatients();

    @GetMapping("/patients/{id}")
    PatientBean getPatientById(@PathVariable("id") UUID id);

    @PostMapping("/patients")
    PatientBean createPatient(@RequestBody PatientBean patient);

    @PutMapping("/patients/{id}")
    PatientBean updatePatient(@PathVariable("id") UUID id, @RequestBody PatientBean patientBean);

    @DeleteMapping("/patients/{id}")
    void deletePatient(@PathVariable("id") UUID id);
}
