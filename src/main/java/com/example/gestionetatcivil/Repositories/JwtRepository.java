package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Long> {
    Optional<Jwt> findByValeur(String valeur);


    void deleteAllByExpirationAndDesactive( Boolean desactive,Boolean expire);
    void deleteByValeur(String valeur);

    @Query("FROM Jwt j WHERE  j.subscriber.email=:email and j.desactive= :expire and j.expiration=: expire" )
    Optional <Jwt> findBytoken(String email, Boolean desactive,Boolean expire);

    @Query("FROM Jwt j WHERE  j.subscriber.email=:email")
    Stream<Jwt> findByAbonne(String email);


    void deleteAllByValeur(String valeur);

}
