package com.example.ufo_fi;

import com.example.ufo_fi.config.TestFirebaseConfig;
import com.example.ufo_fi.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import({TestFirebaseConfig.class, TestSecurityConfig.class})
class UfoFiApplicationTests {

    @Test
    void contextLoads() {
    }

}
