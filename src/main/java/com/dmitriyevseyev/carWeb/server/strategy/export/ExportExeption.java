package com.dmitriyevseyev.carWeb.server.strategy.export;

public class ExportExeption extends Exception {
    public ExportExeption(){
    super();
}

    public ExportExeption (String message){
        super(message);
    }
}
