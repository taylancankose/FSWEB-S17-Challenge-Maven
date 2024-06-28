package com.workintech.spring17challenge.model;

public class Grade {
    private int coefficient;
    private String note;

    public Grade(int coefficient, String note) {
        this.coefficient = coefficient;
        this.note = note;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
