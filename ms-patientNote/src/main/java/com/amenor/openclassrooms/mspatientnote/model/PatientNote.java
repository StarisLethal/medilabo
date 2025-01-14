package com.amenor.openclassrooms.mspatientnote.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Document(collection = "patientNotes")
public class PatientNote {

    @Id
    public UUID patientNoteId;
    @NotNull
    public UUID patientId;
    @NotEmpty
    public String patientNote;
}
