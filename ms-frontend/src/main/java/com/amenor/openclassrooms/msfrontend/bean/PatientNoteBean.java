package com.amenor.openclassrooms.msfrontend.bean;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatientNoteBean {
    public UUID patientNoteId;
    public UUID patientId;
    public String patientName;
    public String patientNote;
}
