package com.todolist.ToDoList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ToDoList;
import java.util.List;

@RestController
public class DefaultController {

    @GetMapping("/todolist/")
    public List<ToDoList> list() {
        return Storage.getAllList();
    }

    @PostMapping("/todolist/")
    public int add(ToDoList toDoList) {
        return Storage.addToDoList(toDoList);
    }

    @DeleteMapping("/todolist/")
    public ResponseEntity deleteAll() {

        if (Storage.getToDoLists().size() == 0) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Storage.deleteToDoList();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity delete(@PathVariable int id) {

        if (Storage.getToDoLists().containsKey(id)) {
            Storage.deleteOneToDoList(id);
            return new ResponseEntity((HttpStatus.OK));
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/todolistedit/{id}/{text}")
    public ResponseEntity edit(@PathVariable String id, @PathVariable String text, ToDoList toDoList) {

        ToDoList toDoListEdit = Storage.getToDoList(Integer.parseInt(id));
        if (toDoListEdit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Storage.editToDoList(toDoList, Integer.parseInt(id), text);
        return new ResponseEntity(toDoList, HttpStatus.OK);
    }

    @GetMapping("/todolist/{id}")
    public ResponseEntity get(@PathVariable int id) {

        ToDoList toDoList = Storage.getToDoList(id);
        if (toDoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(toDoList, HttpStatus.OK);

    }
}
