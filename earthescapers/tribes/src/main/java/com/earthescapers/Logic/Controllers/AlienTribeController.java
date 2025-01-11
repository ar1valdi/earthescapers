package com.earthescapers.Logic.Controllers;

import com.earthescapers.Logic.Services.AlienTribesService;
import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.DTO.AlienTribeDTO;
import com.earthescapers.Models.DTO.AlienTribeListDTO;
import com.earthescapers.Models.requests.CreateAlienTribeRequest;
import com.earthescapers.Models.requests.UpdateAlienTribeRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tribes")
public class AlienTribeController {
    @Autowired
    private AlienTribesService _tribesService;

    @GetMapping
    public ResponseEntity<AlienTribeListDTO> getAllTribes() {
        var tDto = _tribesService
                .getAllTribes()
                .stream()
                .map(t -> new AlienTribeDTO(t))
                .toList();

        if (tDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AlienTribeListDTO(tDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlienTribeDTO> getTribesById(@PathVariable UUID id) {
        var t = _tribesService.getById(id);
        return t.map(tr -> ResponseEntity.ok(new AlienTribeDTO(tr))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createTribe(@RequestBody CreateAlienTribeRequest request) {
        AlienTribe t = new AlienTribe.AlienTribeBuilder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .population(request.getPopulation())
                .spaceshipSpeed(request.getSpaceshipSpeed())
                .build();

        try {
            _tribesService.addTribe(t);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTribe(@PathVariable String id, @RequestBody UpdateAlienTribeRequest request) {
        AlienTribe t = new AlienTribe.AlienTribeBuilder()
                .name(request.getName())
                .population(request.getPopulation())
                .id(UUID.fromString(id))
                .spaceshipSpeed(request.getSpaceshipSpeed())
                .build();

        boolean deleted = _tribesService.updateTribe(t);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        if (!_tribesService.removeTribe(UUID.fromString(id))) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
