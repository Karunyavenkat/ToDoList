package com.todo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class App {
    // ANSI color codes for console
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String CYAN = "\033[0;36m";
    public static final String PURPLE = "\033[0;35m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        TaskDAO taskDAO = new TaskDAO();

        System.out.println(PURPLE + "╔══════════ WELCOME TO TO-DO LIST ══════════╗" + RESET);
        System.out.print(CYAN + "Enter your email: " + RESET);
        String email = sc.nextLine();

        while (!EmailValidator.isValid(email)) {
            System.out.print(RED + "Invalid email! Enter again: " + RESET);
            email = sc.nextLine();
        }

        int userId = userDAO.addUser(email);

        // Start alert scheduler
        TaskAlert alert = new TaskAlert(taskDAO, userId);
        alert.start();

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print(YELLOW + "Choose: " + RESET);
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTask(sc, taskDAO, userId);
                    break;
                case 2:
                    listTasks(taskDAO, userId);
                    break;
                case 3:
                    updateTaskStatus(sc, taskDAO);
                    break;
                case 4:
                    deleteTask(sc, taskDAO);
                    break;
                case 5:
                    exit = true;
                    System.out.println(GREEN + "Goodbye! Thanks for using To-Do List." + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid choice! Try again." + RESET);
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println(PURPLE + "\n╔══════════════ MENU ═════════════╗" + RESET);
        System.out.println("1. Add Task");
        System.out.println("2. List Tasks");
        System.out.println("3. Update Task Status");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
        System.out.println(PURPLE + "╚═════════════════════════════════╝" + RESET);
    }

    private static void addTask(Scanner sc, TaskDAO taskDAO, int userId) {
        System.out.print(CYAN + "Task Name: " + RESET);
        String taskName = sc.nextLine();

        System.out.print(CYAN + "Due Date (D-M-YYYY): " + RESET);
        String inputDate = sc.nextLine();
        Date dueDate;

        try {
            LocalDate localDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("d-M-yyyy"));
            dueDate = Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            System.out.println(RED + "Invalid date format! Task not added." + RESET);
            return;
        }

        taskDAO.addTask(userId, taskName, dueDate);
        System.out.println(GREEN + "✅ Task added successfully!" + RESET);
    }

    private static void listTasks(TaskDAO taskDAO, int userId) {
        List<Task> tasks = taskDAO.getPendingTasks(userId);
        if (tasks.isEmpty()) {
            System.out.println(YELLOW + "No pending tasks!" + RESET);
            return;
        }

        System.out.printf("%-5s %-20s %-12s %-10s%n", "ID", "Task Name", "Due Date", "Status");
        System.out.println("----------------------------------------------------");
        for (Task task : tasks) {
            String color = task.getStatus().equalsIgnoreCase("completed") ? GREEN : YELLOW;
            System.out.printf(color + "%-5d %-20s %-12s %-10s" + RESET + "%n",
                    task.getId(), task.getName(), task.getDueDate(), task.getStatus());
        }
    }

    private static void updateTaskStatus(Scanner sc, TaskDAO taskDAO) {
        System.out.print(CYAN + "Task ID to update: " + RESET);
        int updateId = sc.nextInt();
        sc.nextLine();
        System.out.print(CYAN + "New Status: " + RESET);
        String status = sc.nextLine();
        taskDAO.updateTaskStatus(updateId, status);
        System.out.println(GREEN + "✅ Task status updated!" + RESET);
    }

    private static void deleteTask(Scanner sc, TaskDAO taskDAO) {
        System.out.print(CYAN + "Task ID to delete: " + RESET);
        int deleteId = sc.nextInt();
        sc.nextLine();
        taskDAO.deleteTask(deleteId);
        System.out.println(GREEN + "✅ Task deleted!" + RESET);
    }
}

