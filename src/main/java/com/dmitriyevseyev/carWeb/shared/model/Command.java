package com.dmitriyevseyev.carWeb.shared.model;

import java.io.Serializable;

public class Command implements Serializable  {
    public Integer action;
    public Object data;

    public Command() {
    }

    public Command(Integer action) {
        this.action = action;
    }

    public Command(Integer action, Object data) {
        this.action = action;
        this.data = data;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Command{" +
                "action=" + action +
                ", data=" + data +
                '}';
    }
}
