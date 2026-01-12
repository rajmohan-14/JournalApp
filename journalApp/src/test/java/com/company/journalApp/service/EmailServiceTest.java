package com.company.journalApp.service;

import com.company.journalApp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("jhsay23@gmail.com", "testing mai", "hello from springboot app");
    }
}
