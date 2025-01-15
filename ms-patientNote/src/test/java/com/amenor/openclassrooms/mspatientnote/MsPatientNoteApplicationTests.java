package com.amenor.openclassrooms.mspatientnote;

import com.amenor.openclassrooms.mspatientnote.config.DisableSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MsPatientNoteApplication.class, DisableSecurityConfig.class}, properties = "spring.profiles.active=test")
class MsPatientNoteApplicationTests {

    @Test
    void contextLoads() {
    }

}
