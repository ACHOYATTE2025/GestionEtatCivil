# Projet : Gestion d'État Civil

Ce projet est une API REST en Spring Boot pour la gestion des actes de naissance.

## Fonctionnalités
- Authentification JWT avec Spring Security
- CRUD sur les actes de naissance
- Gestion des rôles : Admin, Agent
- Base de données PostgreSQL

## Technologies
- Java 17
- Spring Boot
- PostgreSQL
- Spring Security, JWT
- Angular (front-end dans un repo séparé)

## Lancer le projet
```bash
# Backend
git clone ...
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
ng serve
