package com.earthescapers;
import com.earthescapers.Models.DTO.PersonDTO;
import com.earthescapers.Models.DTO.PersonListDTO;
import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.Person;
import com.earthescapers.Logic.Services.AlienTribesService;
import com.earthescapers.Logic.Services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {
    private final PeopleService _peopleService;
    private final AlienTribesService _alienTribesService;
    private final DataInitializer _dataInitializer;

    @Autowired
    public Runner(PeopleService ps, AlienTribesService as, DataInitializer di) {
        _peopleService = ps;
        _alienTribesService = as;
        _dataInitializer = di;
    }

    String appWelcomeMessage = "Welcome to Earth Escapers!\nType 'man' for command line manual\n";
    String manContent =
            "man                    - list all commands\n" +
            "list <people | tribes> - lists elements in table\n" +
            "dto <people | tribes>  - lists all tribes as dto\n" +
            "add person             - adds new person kidnapped by tribe wiht tribe_id\n" +
            "remove                 - removes person with given id\n" +
            "stop                   - stops application\n";

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
                    if (command.equals("people")) {
                        _peopleService.getAllPeople().forEach(System.out::println);
                    } else if (command.equals("tribes")) {
                        _alienTribesService.getAllTribes().forEach(System.out::println);
                    }
                }
                case "add" -> {
                    try {
                        addPerson(scanner);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                case "remove" -> {
                    command = scanner.next();
                    UUID id = UUID.fromString(command);
                    _peopleService.removePerson(id);
                    System.out.println("Removed " + command);
                }
                case "dto" -> {
                    command = scanner.next();
                    if (command.equals("people")) {
                        printAllPeopleAsDto();
                    }
                }
                case "stop" -> end = true;
            }
        }
        scanner.close();
    }

    void addPerson(Scanner scanner) {
        String buf = scanner.next();
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Surname: ");
        String surname = scanner.next();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.next());
        System.out.print("Kidnapped by (ID): ");
        UUID alienId = UUID.fromString(scanner.next());
        AlienTribe alienTribe = _alienTribesService.getById(alienId).orElse(null);
        if (alienTribe == null) {
            throw new Error("UUID not found");
        }
        Person p = new Person.PersonBuilder()
                .name(name)
                .surname(surname)
                .age(age)
                .kidnappedBy(alienTribe)
                .id(UUID.randomUUID())
                .build();

        _peopleService.addPerson(p);
        System.out.println("New person added");
    }

    private void printAllPeopleAsDto() {
        var ppl = _peopleService.getAllPeople()
                .stream()
                .map(p -> new PersonDTO(p))
                .toList();

        var dtoList = new PersonListDTO(ppl);
        System.out.println(dtoList);
    }
}
