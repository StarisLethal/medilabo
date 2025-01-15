package com.amenor.openclassrooms.msfrontend;

import com.amenor.openclassrooms.msfrontend.config.DisableSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {MsFrontendApplication.class, DisableSecurityConfig.class}, properties = "spring.profiles.active=test")
class MsFrontendApplicationTests {

    @Test
    void contextLoads() {
    }

}
