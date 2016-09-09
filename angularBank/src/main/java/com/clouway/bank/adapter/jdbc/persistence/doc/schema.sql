CREATE DATABASE bank;

CREATE TABLE users(
name VARCHAR(30),
email VARCHAR (30),
password VARCHAR (16)
);

 create table sessions(
 id VARCHAR (50),
 email varchar(30),
 expirationTime bigint
 );

create table accounts(
 email VARCHAR (50),
 balance double PRECISION
 );

CREATE TABLE users(
name VARCHAR(30),
email VARCHAR (30),
password VARCHAR (16)
);

 create table sessions(
 id VARCHAR (50),
 email varchar(30),
 expirationTime bigint
 );
