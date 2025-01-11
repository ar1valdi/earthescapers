package com.earthescapers.Logic.Services;

import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Logic.Repositories.AlienTribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlienTribesService {
    private final AlienTribesRepository _tribesRepository;
    private final EventPublisherService _eventPublisher;

    @Autowired
    public AlienTribesService(AlienTribesRepository repository, EventPublisherService publisher) {
        _tribesRepository = repository;
        _eventPublisher = publisher;
    }

    public Optional<AlienTribe> getById(UUID id) {
        AlienTribe tribe = _tribesRepository.findById(id).orElse(null);
        return Optional.of(tribe);
    }

    public void addTribe(AlienTribe tribe) {
        _tribesRepository.save(tribe);
        _eventPublisher.notifyCategoryAdded(tribe.getId().toString());
    }

    public List<AlienTribe> getAllTribes() {
        var results = _tribesRepository.findAll();
        return results;
    }

    public boolean updateTribe(AlienTribe t) {
        var a = _tribesRepository.findById(t.getId()).orElse(null);
        if (a == null) {
            return false;
        }
        _tribesRepository.save(t);
        return true;
    }

    public boolean removeTribe(UUID id) {
        var a = _tribesRepository.findById(id);
        if (a.isEmpty()) {
            return false;
        }

        _tribesRepository.deleteById(id);
        _eventPublisher.notifyCategoryRemoved(id.toString());
        return true;
    }
}
