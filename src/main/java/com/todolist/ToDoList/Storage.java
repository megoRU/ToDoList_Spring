package com.todolist.ToDoList;

import response.ToDoList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {

    private static int currentId = 1;
    private static final HashMap<Integer, ToDoList> toDoLists = new HashMap<>();

    public static List<ToDoList> getAllList() {
        return new ArrayList<>(toDoLists.values());
    }

    public static int addToDoList(ToDoList toDoList) {
        int id = currentId++;
        toDoList.setId(id);
        toDoLists.put(id, toDoList);
        return id;
    }

    public static void editToDoList(ToDoList toDoList, Integer id, String text) {
        if (toDoLists.containsKey(id)) {
            toDoList.setId(id);
            toDoList.setText(text);
            toDoLists.put(id, toDoList);
        }
    }

    public static void deleteToDoList() {
        setCurrentId(1);
        toDoLists.clear();

    }

    //TODO: Реализовать сдвиг на -1
    public static void deleteOneToDoList(int id) {

        if (toDoLists.containsKey(id)) {
            setCurrentId(currentId--);
            toDoLists.remove(id);
        }

    }

    public static ToDoList getToDoList(int todolistId) {
        if (toDoLists.containsKey(todolistId)) {
            return toDoLists.get(todolistId);
        }
        return null;
    }

    public static void setCurrentId(int currentId) {
        Storage.currentId = currentId;
    }

    public static HashMap<Integer, ToDoList> getToDoLists() {
        return toDoLists;
    }

}
