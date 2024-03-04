package com.dmitriyevseyev.carWeb.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class CarDealership implements Serializable {
    private Integer id;
    private String name;
    private String adress;
    private HashMap<Integer, Car> carMap;

    public CarDealership() {
    };

    public CarDealership(Integer id, String name, String adress, HashMap<Integer, Car> carMap) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.carMap = carMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public HashMap<Integer, Car> getCarMap() {
        return carMap;
    }

    public void setCarMap(HashMap<Integer, Car> carMap) {
        this.carMap = carMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDealership that = (CarDealership) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(adress, that.adress) && Objects.equals(carMap, that.carMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress, carMap);
    }

    @Override
    public String toString() {
        return "CarDealership{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", carMap=" + carMap +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String adress;
        private HashMap<Integer, Car> carMap;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder adress(String adress) {
            this.adress = adress;
            return this;
        }

        public Builder carMap(HashMap<Integer, Car> carMap) {
            this.carMap = carMap;
            return this;
        }

        public CarDealership build() {
            return new CarDealership(
                    id,
                    name,
                    adress,
                    carMap
            );
        }
    }
}