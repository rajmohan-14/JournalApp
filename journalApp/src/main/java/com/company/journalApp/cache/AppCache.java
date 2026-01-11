package com.company.journalApp.cache;

import com.company.journalApp.entities.ConfigJournalAppEntity;
import com.company.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> cacheMap; // would store the data fetched from the database

    @PostConstruct  // this annotation enusres that as the bean gets loaded, the method gets invoked
    public void init(){
        cacheMap = new HashMap<>(); // initialize everytime to avoid duplicate entry
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity entity: all){
            cacheMap.put(entity.getKey(), entity.getValue());
        }
    }
}
