package com.todolist.ToDoList;

import com.todolist.ToDoList.model.ToDo;
import com.todolist.ToDoList.model.ToDoRepository;
import java.util.ArrayList;
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
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (ToDo toDo : toDoIterable) {
            toDos.add(toDo);
        }
        model.addAttribute("toDos", toDos);
        model.addAttribute("tasksCount", toDoRepository.count());
        return "index";
    }
}
