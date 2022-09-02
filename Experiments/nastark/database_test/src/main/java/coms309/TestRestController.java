package coms309;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.database.Person;
import coms309.database.TestRepository;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/databasetest")
    public @ResponseBody Iterable<Person> databaseTest() {
        return testRepository.findAll();
    }
}
