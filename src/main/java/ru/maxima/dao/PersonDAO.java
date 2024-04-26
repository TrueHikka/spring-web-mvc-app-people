package ru.maxima.dao;

import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.util.ArrayList;
import java.util.List;

//DAO - Data Access Object
@Component
public class PersonDAO {
    private Long PEOPLE_COUNT = 0L;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Ivan", 15));
        people.add(new Person(++PEOPLE_COUNT, "Boris",25));
        people.add(new Person(++PEOPLE_COUNT, "Alex",30));
    }

    public List<Person> getAllPeople() {
        return people;
    }

    public Person findById(Long id) {
        return people.stream()
                .filter(person -> person.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(Person person, Long id) {
        Person personToUpdate = findById(id);
        if (personToUpdate != null) {
            personToUpdate.setName(person.getName());
            personToUpdate.setAge(person.getAge());
        }
    }

    public void delete(Long id) {
        people.removeIf(person -> person.getId().equals(id));
    }
}
