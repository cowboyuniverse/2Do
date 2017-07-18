package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    private String category;
    private int done;

    public ToDoItem(String description, String dueDate, String category, int done ) {
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.done = done;
    }

    public ToDoItem(String description, String dueDate, String category ) {
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
    }

    public ToDoItem(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public int isDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}

