package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.PersonDAO;
import ru.maxima.models.Person;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    //CRUD
    //Create - POST-request (создать)
    //Read - GET-request (получить)
    //Update - Put/Patch-request (изменить)
    //Delete - DELETE-request (удалить)

    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAllPeople(Model model) throws SQLException {
        List<Person> allPeople = personDAO.getAllPeople();
        model.addAttribute("allPeople", allPeople);
        return "people/view-with-all-people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id, Model model) throws SQLException {
        Person person = personDAO.findById(id);
        model.addAttribute("personById", person);
        return "people/view-with-person-by-id";
    }

    @GetMapping("/create")
    public String getPageToCreateNewPerson(Model model){
        model.addAttribute("newPerson", new Person());
        return "people/view-to-create-new-person";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person, BindingResult result) throws SQLException {
        if (result.hasErrors()){
            return "people/view-to-create-new-person";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditPerson(Model model, @PathVariable("id") Long id) throws SQLException {
        Person person = personDAO.findById(id);
        model.addAttribute("editedPerson", person);
        return "people/view-to-edit-person";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id") Long id, @ModelAttribute("editedPerson") @Valid Person person, BindingResult result) throws SQLException {
        if (result.hasErrors()){
            return "people/view-to-edit-person";
        }

        personDAO.update(person, id);
        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Long id) throws SQLException {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
