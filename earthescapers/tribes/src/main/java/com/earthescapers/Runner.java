package com.earthescapers;

import com.earthescapers.Models.DTO.AlienTribeDTO;
import com.earthescapers.Models.DTO.AlienTribeListDTO;
import com.earthescapers.Logic.Services.AlienTribesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {
    private final AlienTribesService _alienTribesService;
    private final DataInitializer _dataInitializer;

    @Autowired
    public Runner(AlienTribesService as, DataInitializer di) {
        _alienTribesService = as;
        _dataInitializer = di;
    }

    String appWelcomeMessage = "Welcome to Earth Escapers!\nType 'man' for command line manual\n";
    String manContent =
            "man                    - list all commands\n" +
            "list tribes            - lists elements in table\n" +
            "dto tribes             - lists all tribes as dto\n" +
            "stop                   - stops application\n" +
            "remove <id>            - removes tribe\n";

    @Override
    public void run(String... args) {
        _dataInitializer.init();
        System.out.println(appWelcomeMessage);

        boolean end = false;
        Scanner scanner = new Scanner(System.in);

        while (!end) {
            String command = scanner.next();
            switch (command) {
                case "man" -> System.out.println(manContent);
                case "list" -> {
                    command = scanner.next();
                    if (command.equals("tribes")) {
                        _alienTribesService.getAllTribes().forEach(System.out::println);
                    }
                }
                case "dto" -> {
                    command = scanner.next();
                    if (command.equals("tribes")) {
                        printAllTribesAsDto();
                    }
                }
                case "remove" -> {
                    command = scanner.next();
                    _alienTribesService.removeTribe(UUID.fromString(command));
                }
                case "stop" -> end = true;
            }
        }
        scanner.close();
    }

    private void printAllTribesAsDto() {
        var tr = _alienTribesService
                .getAllTribes()
                .stream()
                .map(t -> new AlienTribeDTO(t))
                .toList();

        var dtoList = new AlienTribeListDTO(tr);
        System.out.println(dtoList);
    }
}
