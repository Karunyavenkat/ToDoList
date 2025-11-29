package com.todo;

import java.sql.Date;

public class Task {
    private int id;
    private String name;
    private Date dueDate;
    private String status;

    public Task(int id, String name, Date dueDate, String status) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Date getDueDate() { return dueDate; }
    public String getStatus() { return status; }
}
