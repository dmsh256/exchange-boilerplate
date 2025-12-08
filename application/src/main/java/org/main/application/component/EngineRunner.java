package org.main.application.component;

import core.MatchingEngine.MatchingEngine;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EngineRunner {

    private final MatchingEngine matchingEngine;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    public EngineRunner(MatchingEngine matchingEngine) {
        this.matchingEngine = matchingEngine;
    }

    @PostConstruct
    public void start() {
        System.out.println("Engine runner started...");

        executor.submit(matchingEngine);
    }
}