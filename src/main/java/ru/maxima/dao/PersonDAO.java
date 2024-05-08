package ru.maxima.dao;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//DAO - Data Access Object
@Component
public class PersonDAO {

//    {
//        people = new ArrayList<>();
//        people.add(new Person(++PEOPLE_COUNT, "Ivan", 15, "ivan@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Boris", 25, "boris@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Alex", 30, "alex@mail.ru"));
//    }

//    private Connection connection;

//    @PostConstruct
//    public void init() {
//        try {
//            //Проверка на наличие драйвера
//            Class.forName("org.postgresql.Driver");
//            //Проверка на то, что подключение есть
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            System.out.println("Connection initialized: " + connection);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @PreDestroy
//    public void destroy() {
//        try {
//            if (connection != null) {
//                connection.close();
//                System.out.println("Connection closed");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        //        if (connection == null) {
//            System.out.println("Connection is null");
//            throw new RuntimeException("Connection is not initialized");
//        }

//        List<Person> people = new ArrayList<>();

//        try {
//            //Подготавливает запрос к БД, который хотим прокинуть
//            //Промежуточный объект между соединением и самим заныриванием в БД
////            Statement statement = connection.createStatement();
////
////            String SQLQuery = "SELECT * FROM person";
//
//            //Исполнение запроса - statement
//            //resultSet - результат запроса - вернет строки из таблицы БД
////            ResultSet resultSet = statement.executeQuery(SQLQuery);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from person");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Person person = new Person();
//                person.setId(resultSet.getLong("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//
//                people.add(person);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return people;
        List<Person> people = jdbcTemplate.query("select * from person", new PersonMapper()); //Заменили new BeanPropertyRowMapper<>(Person.class)
        return people;
    }

    public Person findById(Long id) {
//        Person person = new Person();
//        return people.stream()
//                .filter(person -> person.getId().equals(id))
//                .findAny()
//                .orElse(null);
//        Statement statement = connection.createStatement();
//
//        String SQLQuery = "select * from person where id = " + id;
//
//        ResultSet resultSet = statement.executeQuery(SQLQuery);

//        PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id =?");
//
//        preparedStatement.setLong(1, id);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            person.setId(resultSet.getLong("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        }
//
//        return person;
        return jdbcTemplate.queryForObject("select * from person where id =?", new Object[]{id}, new PersonMapper()); //Заменили new BeanPropertyRowMapper<>(Person.class)
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
        //        Statement statement = connection.createStatement();
//
//        String SQLQuery = "insert into person(id, name, email, age)" +
//                "values ("+ id + ", '" + person.getName() + "', '" + person.getEmail() + "', " + person.getAge() + ")";
//
//        statement.executeUpdate(SQLQuery);

        //getALlPeople -> List<Person>
        //.map() -> List<Long>

//        Long id = person.getId();
//
//        if (id == null || id == 0) {
//            id = getAllPeople().stream()
//                    .map(Person::getId)
//                    .max(Comparator.naturalOrder())
//                    .orElse(0L) + 1;
//        }
//
        if (person.getId() == null) {
            List<Person> allPeople = getAllPeople();
            if (!allPeople.isEmpty()) {
                person.setId(allPeople.stream()
                        .map(Person::getId)
                        .max(Comparator.naturalOrder())
                        .orElse(0L) + 1);
            }
        }

//        PreparedStatement preparedStatement = connection.prepareStatement("insert into person(id, name, email, age)" + "values (?,?,?,?)");
//
//        preparedStatement.setLong(1, id);
//        preparedStatement.setString(2, person.getName());
//        preparedStatement.setString(3, person.getEmail());
//        preparedStatement.setInt(4, person.getAge());
//
//        preparedStatement.executeUpdate();

        jdbcTemplate.update("insert into person(id, name, email, age) values (?,?,?,?)", person.getId(), person.getName(), person.getEmail(), person.getAge());
    }

    public void update(Person person, Long id) {
//        Person personToUpdate = findById(id);
//        if (personToUpdate != null) {
//            personToUpdate.setName(person.getName());
//            personToUpdate.setAge(person.getAge());
//        }
//        Statement statement = connection.createStatement();
//
//        String SQLQuery = "update person set name = '" + person.getName() + "', age = " + person.getAge() + " where id = " + id;
//
//        statement.executeUpdate(SQLQuery);

//        PreparedStatement preparedStatement = connection.prepareStatement("update person set name =?, age =? where id =?");
//
//        preparedStatement.setString(1, person.getName());
//        preparedStatement.setInt(2, person.getAge());
//        preparedStatement.setLong(3, id);
//
//        preparedStatement.executeUpdate();
        jdbcTemplate.update("update person set name =?, age =? where id =?", person.getName(), person.getAge(), id);
    }

    public void delete(Long id) {
//        people.removeIf(person -> person.getId().equals(id));
//        Statement statement = connection.createStatement();
//        String SQLQuery = "delete from person where id = " + id;
//        statement.executeUpdate(SQLQuery);

//        PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id =?");
//
//        preparedStatement.setLong(1, id);
//
//        preparedStatement.executeUpdate();
        jdbcTemplate.update("delete from person where id =?", id);

    }
}
