package com.sargent.mark.todolist;

/**
 * Created by cowboyuniverse on 7/16/17.
 */

public class SpinnerClass {
    private int id;
    private String value;

    public SpinnerClass(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
