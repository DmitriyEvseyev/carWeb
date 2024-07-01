package com.dmitriyevseyev.carWeb.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String password;

    public User() {
    }

    public User(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String userName;
        private String password;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password (String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(
                    id,
                    userName,
                    password
            );
        }
    }
}


