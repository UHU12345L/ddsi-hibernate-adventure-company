# DDSI – Hibernate Adventure Company

Lab assignment 1 (part 1) for *Diseño y Desarrollo de Sistemas de Información* (Information Systems Design and Development), Computer Engineering, Universidad de Huelva (2026/27).

A Java console application that accesses a MariaDB database of an adventure-tourism company through **Hibernate ORM**.

## What it does

A menu with 10 read queries over the model (Cliente, Guia, Aventura, Turno, Reserva), showing the different ways Hibernate can query a database:

- **HQL** queries, with and without parameters
- **Native SQL** queries
- **Named queries** (HQL and native SQL)
- **Projections** (only some columns, as `Object[]`)
- Single vs. list results

Each operation uses its own `Session` and `Transaction` (commit / rollback / close).

## Tech

Java · Maven · Hibernate 7 · Jakarta Persistence 3.2 · MariaDB · NetBeans

## Structure

```
Practica1_DDSI/src/main/java/
  Config/      HibernateUtil (SessionFactory)
  Modelo/      JPA entities
  Aplicacion/  Practica1 (main menu + queries)
Practica1_DDSI/src/main/resources/hibernate.cfg.xml
```

## Running it

The database is on a University of Huelva internal server, so it only works from the university network. Set your own user and password in `hibernate.cfg.xml` (the password in this repo is a placeholder).
