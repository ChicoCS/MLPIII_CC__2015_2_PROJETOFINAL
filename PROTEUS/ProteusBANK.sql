create database proteusbank;

create table conta(
id serial not null,
numero varchar(20) not null,
nome varchar(50) not null,
saldo numeric(9,2) not null,
primary key(id)
);