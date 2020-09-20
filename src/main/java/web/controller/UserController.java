package crud.controllers;

import web.model.User;
import web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List list = userService.getAllUsers();
        model.addAttribute("users", list);
        return "admin";
    }

    @GetMapping("/users/new")  // по /users/new вернется форма для создания человека
    public String createPerson (User user) { // если используем таймлиф, мы должны передать объект, для которого форма нужна
        return "new";
    }

    @PostMapping("/users/new")
    public String create (User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String deleteUser (@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/update/{id}")
    public String updatePerson (@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        System.out.println(user);
        model.addAttribute("user", user);
        return "update";
    }
    @PostMapping("/update")
    public String update (@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

}
