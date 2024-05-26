package com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption;

public class DealerNAmeAddressAlreadyExistException extends Exception {
    public DealerNAmeAddressAlreadyExistException(){
        super();
    }

    public DealerNAmeAddressAlreadyExistException(String message){
        super(message);
    }
}