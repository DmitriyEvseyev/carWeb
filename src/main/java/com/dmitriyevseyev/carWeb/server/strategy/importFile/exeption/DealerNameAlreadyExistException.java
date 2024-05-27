package com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption;

public class DealerNameAlreadyExistException extends Exception {
    public DealerNameAlreadyExistException(){
        super();
    }

    public DealerNameAlreadyExistException(String message){
        super(message);
    }
}