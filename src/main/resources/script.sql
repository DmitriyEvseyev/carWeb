CREATE TABLE IF NOT EXISTS DEALERS
(
    ID      INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    NAME    VARCHAR         NOT NULL,
    ADDRESS VARCHAR         NOT NULL
);
CREATE TABLE IF NOT EXISTS CAR
(
    ID           INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    IDDEALER     INT,
    NAME         VARCHAR         NOT NULL,
    DATE         DATE            NOT NULL,
    COLOR        VARCHAR         NOT NULL,
    ISAFTERCRASH BOOLEAN,
    FOREIGN KEY (IDDEALER) REFERENCES DEALERS (ID) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS USERS
(
    ID       INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    USERNAME VARCHAR         NOT NULL,
    PASSWORD VARCHAR         NOT NULL
);




