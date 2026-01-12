package com.company.journalApp.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FetchUserAndSendEmailTest {

    @Autowired
    private EmailScheduler emailScheduler;

    @Test
    public void testFetchUserAndSendEmail(){
        emailScheduler.fetchUserAndSendEmail();
    }
}
