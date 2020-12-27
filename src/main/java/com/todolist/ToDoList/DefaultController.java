package com.todolist.ToDoList;

import com.todolist.ToDoList.model.ToDoRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private final Connection conn = DriverManager.getConnection(CONN, USER, PASS);
    private static final String CONN = "jdbc:mysql://45.138.72.66:3306/todolist_spring?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "todolist_spring";
    private static final String PASS = "Omhd34_4";

    public DefaultController() throws SQLException {
    }

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
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();

        if (!toDoIterable.iterator().hasNext()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        toDoRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity delete(@PathVariable int id) throws SQLException {
        Optional<ToDo> toDoIterable = toDoRepository.findById(id);

        if (toDoIterable.isPresent()) {
            toDoRepository.delete(toDoIterable.get());
            Storage storage = new Storage();
            int sumId = storage.num() + 1;
            String query4 = "UPDATE to_do SET id = id - 1 WHERE id >= ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query4);
            preparedStmt2.setInt(1, toDoIterable.get().getId());
            preparedStmt2.executeUpdate();
            storage.setNextValue(sumId);

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todolistedit/")
    public ResponseEntity edit(@PathVariable ToDo toDo) {
        Optional<ToDo> toDoIterable = toDoRepository.findById(toDo.getId());
        // ToDo newTodo = toDoRepository.save(toDo);

        if (!toDoIterable.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        toDoRepository.save(toDo);
        return new ResponseEntity(toDo, HttpStatus.OK);
    }

    @GetMapping("/todolist/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<ToDo> toDoIterable = toDoRepository.findById(id);

        if (!toDoIterable.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(toDoIterable, HttpStatus.OK);
    }
}
