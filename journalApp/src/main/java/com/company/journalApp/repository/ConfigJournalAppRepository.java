package com.company.journalApp.repository;

import com.company.journalApp.entities.ConfigJournalAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
