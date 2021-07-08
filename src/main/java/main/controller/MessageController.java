package main.controller;

import main.dao.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.repository.ToDoRepository;
import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {

    private final ToDoRepository toDoRepository;

    @Autowired
    public MessageController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping
    public List<Map<String, String>> list() {
        List<Map<String, String>> messages = new ArrayList<>();
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        for (ToDo toDo : toDoIterable) {
            messages.add(new HashMap<>() {
                {
                    put("id", String.valueOf(toDo.getId()));
                    put("text", toDo.getText());
                }
            });
        }
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        Map<String, String> messages = new HashMap<>();

        Optional<ToDo> todo = toDoRepository.findById(Integer.parseInt(id));
        messages.put(String.valueOf(todo.get().getId()), todo.get().getText());

        return messages;
    }

    @PostMapping//Save
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        ToDo toDo = new ToDo();
        toDo.setText(message.toString().substring(6, message.toString().length() - 1));
        toDoRepository.save(toDo);
        System.out.println("Create");
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = new HashMap<>();

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        toDoRepository.update(Integer.parseInt(id), message.toString().substring(6, message.toString().length() - 1));
        System.out.println("update");

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        toDoRepository.deleteById(Integer.parseInt(id));
    }
}
