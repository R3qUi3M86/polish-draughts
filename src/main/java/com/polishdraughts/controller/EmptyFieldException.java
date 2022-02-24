package com.polishdraughts.controller;

public class EmptyFieldException extends Exception{
    Integer fieldNo;

    public EmptyFieldException(Integer fieldNo){
        this.fieldNo = fieldNo;
    }

    public void displayError(int fieldNo){
        System.err.println("There is no pawn in this field! [" + fieldNo + "]");
    }
}
