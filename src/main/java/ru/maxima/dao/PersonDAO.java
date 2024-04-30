package ru.maxima.dao;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//DAO - Data Access Object
@Component
public class PersonDAO {
    private Long PEOPLE_COUNT = 0L;
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "KeK$89052724546$";

//    {
//        people = new ArrayList<>();
//        people.add(new Person(++PEOPLE_COUNT, "Ivan", 15, "ivan@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Boris", 25, "boris@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Alex", 30, "alex@mail.ru"));
//    }

    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            //Проверка на наличие драйвера
            Class.forName("org.postgresql.Driver");
            //Проверка на то, что подключение есть
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection initialized: " + connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

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

    public List<Person> getAllPeople() throws SQLException {
        List<Person> people = new ArrayList<>();

//        if (connection == null) {
//            System.out.println("Connection is null");
//            throw new RuntimeException("Connection is not initialized");
//        }

        try {
            //Подготавливает запрос к БД, который хотим прокинуть
            //Промежуточный объект между соединением и самим заныриванием в БД
            Statement statement = connection.createStatement();

            String SQLQuery = "SELECT * FROM person";

            //Исполнение запроса - statement
            //resultSet - результат запроса - вернет строки из таблицы БД
            ResultSet resultSet = statement.executeQuery(SQLQuery);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person findById(Long id) throws SQLException {
        Person person = new Person();
//        return people.stream()
//                .filter(person -> person.getId().equals(id))
//                .findAny()
//                .orElse(null);
//        Statement statement = connection.createStatement();
//
//        String SQLQuery = "select * from person where id = " + id;
//
//        ResultSet resultSet = statement.executeQuery(SQLQuery);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            person.setId(resultSet.getLong("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        }

        return person;
    }

    public void save(Person person) throws SQLException {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
        Long id = person.getId();

        if (id == null || id == 0) {
            id = getAllPeople().stream()
                    .map(Person::getId)
                    .max(Comparator.naturalOrder())
                    .orElse(0L) + 1;
        }

        Statement statement = connection.createStatement();

        String SQLQuery = "insert into person(id, name, email, age)" +
                "values ("+ id + ", '" + person.getName() + "', '" + person.getEmail() + "', " + person.getAge() + ")";

        statement.executeUpdate(SQLQuery);
    }

    public void update(Person person, Long id) throws SQLException {
//        Person personToUpdate = findById(id);
//        if (personToUpdate != null) {
//            personToUpdate.setName(person.getName());
//            personToUpdate.setAge(person.getAge());
//        }
        Statement statement = connection.createStatement();

        String SQLQuery = "update person set name = '" + person.getName() + "', age = " + person.getAge() + " where id = " + id;

        statement.executeUpdate(SQLQuery);

    }

    public void delete(Long id) throws SQLException {
//        people.removeIf(person -> person.getId().equals(id));
        Statement statement = connection.createStatement();
        String SQLQuery = "delete from person where id = " + id;
        statement.executeUpdate(SQLQuery);

    }
}
