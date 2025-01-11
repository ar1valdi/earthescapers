package com.earthescapers.Logic.Controllers;
import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.Event;

import com.earthescapers.Logic.Services.AlienTribesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventListenerController {
    @Autowired
    private AlienTribesService _tribesService;

    @PostMapping
    public void handleEvent(@RequestBody Event event) {
        System.out.println("Received event: " + event.getType() +". Payload: " + event.getPayload());
        if ("TRIBE_ADDED".equals(event.getType())) {
            var newTribe = new AlienTribe.AlienTribeBuilder().id(UUID.fromString(event.getPayload())).build();
            _tribesService.addTribe(newTribe);
        } else if ("TRIBE_REMOVED".equals(event.getType())) {
            _tribesService.removeTribe(UUID.fromString(event.getPayload()));
        }
    }
}

