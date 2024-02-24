package com.example.demo5.controller;

import com.example.demo5.model.CarDealership;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DealerList {
    private static DealerList instance;
    private List<CarDealership> dealerL;

    public static DealerList getInstance() {
        if (instance == null) {
            instance = new DealerList();
        }
        return instance;
    }

    public DealerList() {
        dealerL = new ArrayList<>();
        CarDealership dealer1 = null;
        CarDealership dealer2 = null;

        dealer1 = CarDealership.builder()
                .id(1)
                .name("Rolf")
                .adress("Moscow")
                .carMap(new HashMap<>())
                .build();

        dealer2 = CarDealership.builder()
                .id(2)
                .name("Alfa")
                .adress("Samara")
                .carMap(new HashMap<>())
                .build();

        dealerL.add(dealer1);
        dealerL.add(dealer2);
        System.out.println("carList from DealerList - " + dealerL);
    }

    public List<CarDealership> getDealerL() {
        return dealerL;
    }

    public void setDealerL(List<CarDealership> dealerL) {
        this.dealerL = dealerL;
    }

    public CarDealership searchDealer(int idDealer) {
        CarDealership dealer = null;
        for (CarDealership c : dealerL) {
            if (c.getId().equals(idDealer)) {
                dealer = c;
            }
        }
        return dealer;
    }
}

