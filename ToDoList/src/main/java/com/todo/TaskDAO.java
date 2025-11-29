package com.todo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(int userId, String taskName, Date dueDate) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO tasks(user_id, task_name, due_date, status) VALUES(?, ?, ?, 'Pending')";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, taskName);
            ps.setDate(3, dueDate);
            ps.executeUpdate();
            System.out.println("Task added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listTasks(int userId) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM tasks WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\nYour Tasks:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                        rs.getString("task_name") +
                        " | Due: " + rs.getDate("due_date") +
                        " | Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskStatus(int taskId, String status) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE tasks SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, taskId);
            ps.executeUpdate();
            System.out.println("Task status updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM tasks WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.executeUpdate();
            System.out.println("Task deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getPendingTasks(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, task_name, due_date, status FROM tasks WHERE user_id = ? AND status != 'Completed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("task_name"),
                        rs.getDate("due_date"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
