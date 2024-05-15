package com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption;

public class DealerIdAlreadyExistException extends Exception {
    public DealerIdAlreadyExistException(){
        super();
    }

    public DealerIdAlreadyExistException(String message){
        super(message);
    }
}