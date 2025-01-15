package com.amenor.openclassrooms.msgateway;

import com.amenor.openclassrooms.msgateway.config.DisableSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MsGatewayApplication.class, DisableSecurityConfig.class}, properties = "spring.profiles.active=test")
class MsGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

}
