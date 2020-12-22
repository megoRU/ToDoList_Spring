package com.todolist.ToDoList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("tasksCount", Storage.getToDoLists().size());
        return "index";
    }
}
