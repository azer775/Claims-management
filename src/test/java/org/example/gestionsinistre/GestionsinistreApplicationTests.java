package org.example.gestionsinistre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties={
        "spring.datasource.url=jdbc:mysql://localhost:3306/StageDB",
        "spring.datasource.username=root",
        "spring.datasource.password="
})
class GestionsinistreApplicationTests {

    @Test
    void contextLoads() {
    }

}
