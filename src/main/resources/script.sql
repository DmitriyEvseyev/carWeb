CREATE TABLE IF NOT EXISTS DEALERS
(
    dealer_id      INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    dealer_name    VARCHAR         NOT NULL,
    dealer_address VARCHAR         NOT NULL
);
CREATE TABLE IF NOT EXISTS CARS
(
    car_id           INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    dealer_id        INT,
    car_name         VARCHAR         NOT NULL,
    car_date         DATE            NOT NULL,
    car_color        VARCHAR         NOT NULL,
    is_after_crash   BOOLEAN,
    FOREIGN KEY (dealer_id) REFERENCES DEALERS (dealer_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS USERS
(
    user_id       INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_name VARCHAR         NOT NULL,
    user_password VARCHAR         NOT NULL
);



