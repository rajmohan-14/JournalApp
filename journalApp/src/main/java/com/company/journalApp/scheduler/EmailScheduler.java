package com.company.journalApp.scheduler;

import com.company.journalApp.cache.AppCache;
import com.company.journalApp.entities.JournalEntry;
import com.company.journalApp.entities.User;
import com.company.journalApp.enums.Sentiment;
import com.company.journalApp.repository.UserRepositoryImpl;
import com.company.journalApp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private Sentiment sentiment;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 8 * * SUN")
    public void fetchUserAndSendEmail(){
        List<User> users = userRepository.getUsersForSA();

        for(User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                maxCount = entry.getValue();
                mostFrequentSentiment = entry.getKey();
            }

            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment of this week", mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
