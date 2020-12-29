package com.todolist.ToDoList;

import com.todolist.ToDoList.model.ToDoRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.todolist.ToDoList.model.ToDo;
import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/todolist/")
    public List<ToDo> list() {
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (ToDo toDo : toDoIterable) {
            toDos.add(toDo);
        }
        return toDos;
    }

    @PostMapping("/todolist/")
    public int add(ToDo toDo) {
        ToDo newTodo = toDoRepository.save(toDo);
        return newTodo.getId();
    }

    @DeleteMapping("/todolist/")
    public ResponseEntity deleteAll() {
        toDoRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        Optional<ToDo> toDoIterable = toDoRepository.findById(id);

        if (toDoIterable.isEmpty()) { // если дела нет - бросаем 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        toDoRepository.delete(toDoIterable.get()); //удаляем дело и возвращаем 200
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/todolist/")
    public ResponseEntity edit(@PathVariable ToDo toDo) {
        Optional<ToDo> toDoIterable = toDoRepository.findById(toDo.getId());

        if (toDoIterable.isEmpty()) { // если дела нет - бросаем 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        toDoRepository.save(toDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/todolist/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<ToDo> toDoIterable = toDoRepository.findById(id);

        if (!toDoIterable.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(toDoIterable, HttpStatus.OK);
    }
}
