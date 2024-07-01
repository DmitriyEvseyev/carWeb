package com.dmitriyevseyev.carWeb.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class CarDealershipDTO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    public CarDealershipDTO() {
    }

    public CarDealershipDTO(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDealershipDTO that = (CarDealershipDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "CarDealershipDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String address;

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

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public CarDealershipDTO build() {
            return new CarDealershipDTO(
                    id,
                    name,
                    address
            );
        }
    }
}