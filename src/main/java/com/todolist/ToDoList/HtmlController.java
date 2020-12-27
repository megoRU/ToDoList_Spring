package com.todolist.ToDoList;

import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping(value = "/")
    public String index(Model model) throws SQLException {
        Storage storage = new Storage();
        model.addAttribute("tasksCount", storage.num());
        return "index";
    }
}