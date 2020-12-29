package com.todolist.ToDoList;

import com.todolist.ToDoList.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("tasksCount", toDoRepository.count());
        return "index";
    }
}
