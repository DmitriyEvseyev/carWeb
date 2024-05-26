package com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption;

public class DealerIdAlreadyExException  extends Exception {
    public DealerIdAlreadyExException(){
        super();
    }

    public DealerIdAlreadyExException(String message){
        super(message);
    }
}
