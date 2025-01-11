package com.earthescapers;

import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Logic.Services.AlienTribesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer {
    private final AlienTribesService _alienTribeService;


    @Autowired
    DataInitializer(AlienTribesService as) {
        _alienTribeService = as;
    }


    public void init() {
        AlienTribe a1 = new AlienTribe.AlienTribeBuilder()
                .id(UUID.fromString("0e65a49a-5e9b-4530-ac14-a1c2ac50c1a7"))
                .name("Xamarin")
                .population(100_000)
                .spaceshipSpeed(500)
                .build();
        AlienTribe a2 = new AlienTribe.AlienTribeBuilder()
                .id(UUID.fromString("4389755f-45e9-4a72-92b2-bd553dfb58c4"))
                .name("Arch")
                .population(200_000)
                .spaceshipSpeed(300)
                .build();

        _alienTribeService.addTribe(a1);
        _alienTribeService.addTribe(a2);
    }
}
