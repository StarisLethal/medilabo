package com.amenor.openclassrooms.mspatients;

import com.amenor.openclassrooms.mspatients.config.DisableSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MsPatientsApplication.class, DisableSecurityConfig.class}, properties = "spring.profiles.active=test")
class MsPatientsApplicationTests {

    @Test
    void contextLoads() {
    }

}
