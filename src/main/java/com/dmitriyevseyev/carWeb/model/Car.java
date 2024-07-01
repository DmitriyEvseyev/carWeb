package com.dmitriyevseyev.carWeb.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "CARS")
public class Car implements Serializable {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
 //   @JsonIgnore
    private CarDealership dealer;
    @Column(name = "car_name")
    private String name;
    @Column(name = "car_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "car_color")
    private String color;
    @Column(name = "is_after_crash")
    private boolean isAfterCrash;

    public Car() {
    }

    public Car(Integer id, CarDealership dealer, String name, Date date, String color, boolean isAfterCrash) {
        this.id = id;
        this.dealer = dealer;
        this.name = name;
        this.date = date;
        this.color = color;
        this.isAfterCrash = isAfterCrash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CarDealership getDealer() {
        return dealer;
    }

    public void setDealer(CarDealership dealer) {
        this.dealer = dealer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAfterCrash() {
        return isAfterCrash;
    }

    public void setAfterCrash(boolean isafterCrash) {
        this.isAfterCrash = isafterCrash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isAfterCrash == car.isAfterCrash &&
                Objects.equals(id, car.id) &&
                Objects.equals(dealer.getId(), car.dealer.getId()) &&
                Objects.equals(name, car.name) &&
                Objects.equals(date, car.date) &&
                Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dealer.getId(), name, date, color, isAfterCrash);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", dealerId=" + dealer.getId() +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", color='" + color + '\'' +
                ", isAfterCrash=" + isAfterCrash +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private CarDealership dealer;
        private String name;
        private Date date;
        private String color;
        private boolean isAfterCrash;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder dealer(CarDealership dealer) {
            this.dealer = dealer;

            return this;
        }
        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder date(Date date) {
            this.date = date;

            return this;
        }

        public Builder color(String color) {
            this.color = color;

            return this;
        }

        public Builder isAfterCrash(boolean isAfterCrash) {
            this.isAfterCrash = isAfterCrash;

            return this;
        }

        public Car build() {
            return new Car(
                    id,
                    dealer,
                    name,
                    date,
                    color,
                    isAfterCrash
            );
        }
    }
}


