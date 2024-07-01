package com.dmitriyevseyev.carWeb.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CarDTO implements Serializable {
    private Integer id;

    //   @JsonIgnore
    private Integer idDealer;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    private String color;

    private boolean isAfterCrash;

    public CarDTO() {
    }

    public CarDTO(Integer id, Integer idDealer, String name, Date date, String color, boolean isAfterCrash) {
        this.id = id;
        this.idDealer = idDealer;
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

    public Integer getIdDealer() {
        return idDealer;
    }

    public void setIdDealer(Integer idDealer) {
        this.idDealer = idDealer;
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
        CarDTO carDTO = (CarDTO) o;
        return isAfterCrash == carDTO.isAfterCrash &&
                Objects.equals(id, carDTO.id) &&
                Objects.equals(idDealer, carDTO.idDealer) &&
                Objects.equals(name, carDTO.name) &&
                Objects.equals(date, carDTO.date) &&
                Objects.equals(color, carDTO.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,idDealer, name, date, color, isAfterCrash);
    }

    @Override
    public String toString() {
        return "carDTO{" +
                "id=" + id +
                ", idDealer=" + idDealer +
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
        private Integer idDealer;
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

        public Builder idDealer(Integer idDealer) {
            this.idDealer = idDealer;

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

        public CarDTO build() {
            return new CarDTO(
                    id,
                    idDealer,
                    name,
                    date,
                    color,
                    isAfterCrash
            );
        }
    }
}


