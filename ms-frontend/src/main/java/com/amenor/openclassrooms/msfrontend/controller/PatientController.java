package com.amenor.openclassrooms.msfrontend.controller;

import com.amenor.openclassrooms.msfrontend.bean.PatientBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.amenor.openclassrooms.msfrontend.proxies.PatientProxy;

import java.util.List;
import java.util.UUID;

@Controller
public class PatientController {

    private final PatientProxy patientProxy;


    public PatientController(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<PatientBean> patients = patientProxy.getPatients();
            model.addAttribute("patients", patients);
        } catch (Exception e) {
            System.err.println("Error fetching patients: " + e.getMessage());
        }
        return "home";
    }

    @GetMapping("/newPatient")
    public String newPatient(Model model) {
        model.addAttribute("patient", new PatientBean());
        return "newPatient";
    }

    @PostMapping("/createPatient")
    public String createPatient(PatientBean patientBean) {
        patientProxy.createPatient(patientBean);
        return "redirect:";
    }

    @GetMapping("/editRecord")
    public String editRecord(Model model, UUID id) {
        model.addAttribute("patient", patientProxy.getPatientById(id));
        model.addAttribute("id", id);
        return "editRecord";
    }

    @PutMapping("/editedRecord")
    public String editRecord(@ModelAttribute PatientBean patientBean, Model model) {
        model.addAttribute("patient", patientProxy.getPatientById(patientBean.getId()));
        patientProxy.updatePatient(patientBean.getId(), patientBean);
        return "redirect:";
    }

    @GetMapping("/deletePatient")
    public String deletePatient(@ModelAttribute PatientBean patientBean, Model model) {
        model.addAttribute("patient", patientProxy.getPatientById(patientBean.getId()));
        return "deletePatient";
    }

    @DeleteMapping("/confirmedDelete")
    public String confirmedDelete(@ModelAttribute PatientBean patientBean) {
        patientProxy.deletePatient(patientBean.getId());
        return "redirect:";
    }
}
