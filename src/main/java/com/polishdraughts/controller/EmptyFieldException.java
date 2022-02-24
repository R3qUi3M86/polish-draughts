package com.polishdraughts.controller;

public class EmptyFieldException extends Exception{
    public EmptyFieldException(int fieldNo){
        System.err.println("There is no pawn in this field! [" + fieldNo + "]");
    }
}
