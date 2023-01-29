DROP TABLE IF EXIST users;
CREATE TABLE users AS SELECT * FROM  CSVREAD('classpath:users.csv');
