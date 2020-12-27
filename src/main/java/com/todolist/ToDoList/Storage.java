package com.todolist.ToDoList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Storage {

    private final Connection conn = DriverManager.getConnection(CONN, USER, PASS);
    private static final String CONN = "jdbc:mysql://45.138.72.66:3306/todolist_spring?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "todolist_spring";
    private static final String PASS = "Omhd34_4";

    public Storage() throws SQLException {
    }

    public int num() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) AS id FROM to_do");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setNextValue(Integer count) {
        try {
            String resultSet = "UPDATE `hibernate_sequence` SET next_val = " + count;
            PreparedStatement preparedStmt2 = conn.prepareStatement(resultSet);
            preparedStmt2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
