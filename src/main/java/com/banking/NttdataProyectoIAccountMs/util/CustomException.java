package com.banking.NttdataProyectoIAccountMs.util;




public class CustomException  extends  RuntimeException{

    private final String message;
    private final int status;

    public CustomException(String message, int status) {
        super(message); // Llama al constructor de la clase base
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
