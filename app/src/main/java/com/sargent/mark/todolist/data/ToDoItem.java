package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    private String category;

//    private String description2;

    public ToDoItem(String description, String dueDate, String category) {
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
    }

    public ToDoItem(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

//    public ToDoItem(String description, String dueDate, String description2) {
//        this.description = description;
//        this.dueDate = dueDate;
//        this.description2 = description2;
//    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getDescription2() {
//        return description2;
//    }
//
//    public void setDescription2(String description2) {
//        this.description2 = description2;
//    }


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

