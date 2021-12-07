package ru.jegensomme.citingvkbot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Import(TestApplicationConfig::class)
class CitingVkBotApplicationTests {
    @Test
    fun contextLoads() {
    }
}
