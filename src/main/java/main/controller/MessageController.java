package main.controller;

import lombok.AllArgsConstructor;
import main.api.request.MessageEditRequest;
import main.api.request.MessageRequest;
import main.model.ListTODO;
import main.repository.ListTODORepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("message")
public class MessageController {

    private final ListTODORepository toDoRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ListTODO> todoList = toDoRepository.getListTODO();
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        List<ListTODO> todoList = toDoRepository.getById(Integer.parseInt(id));
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    //Сохранение
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MessageRequest message) {

        if (message.getText() != null) {
            ListTODO toDo = new ListTODO();
            toDo.setText(message.getText());
            toDoRepository.save(toDo);

            message.setId(toDo.getId());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody MessageEditRequest messageEditRequest) {
        if (id != null && messageEditRequest.getText() != null) {
            toDoRepository.update(Integer.parseInt(id), messageEditRequest.getText());
            messageEditRequest.setId(id);
            return new ResponseEntity<>(messageEditRequest, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        if (id != null) {
            toDoRepository.deleteById(Integer.parseInt(id));
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
