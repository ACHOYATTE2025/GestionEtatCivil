package com.example.gestionetatcivil.Security;

import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Jwt;
import com.example.gestionetatcivil.Repositories.AccountRepository;
import com.example.gestionetatcivil.Repositories.JwtRepository;
import com.example.gestionetatcivil.Service.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
@Transactional
public class JwtService {
    String  CODE_KEY = "qsd4qs86fqs54c8qs94d856qs4d8/s4d56qs1d89qs4dx56qs1d86qs4";
    private final AccountService subscriberService;
    AccountRepository subscriberRepository;
    JwtRepository jwtRepository;

    public JwtService(AccountRepository subscriberRepository, JwtRepository jwtRepository,
                      AccountService subscriberService) {
        this.subscriberRepository = subscriberRepository;
        this.jwtRepository = jwtRepository;
        this.subscriberService = subscriberService;
    }


  public Map<String,String> generate(String email){
        Account subscriber = this.subscriberRepository
                .findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        return this.generateJwt(subscriber);
    }

    // generation token
    public Map<String, String> generateToken(String email) {
        Account subscriber = (Account) this.subscriberService.loadUserByUsername(email);
        this.disableToken(subscriber);
        final Map<String, String> jwtMap = this.generateJwt(subscriber);
        String jwtbearer = jwtMap.toString().substring(8);

        Jwt jwtbuild =
                Jwt.builder()
                        .valeur(jwtbearer.substring(0,jwtbearer.length()-1))
                        .desactive(false)
                        .expiration(false)
                        .subscriber(subscriber)
                        .build();
        this.jwtRepository.save(jwtbuild);

        return jwtMap;

    }

    // generate jwt
    private Map<String, String> generateJwt(Account subscriber) {
        long currentTime = System.currentTimeMillis();
        long expirationTime= currentTime + 30*60*1000;
        Map<String, String> claimsi = Map.of("nom", subscriber.getUsername(),"email", subscriber.getEmail());
        String bearer = Jwts.builder()
                .setClaims(claimsi)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .subject(subscriber.getEmail())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);

    }



    private Key getKey() {

        final byte[] decode = Decoders.BASE64.decode(CODE_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token) {return this.getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());

    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


    public Jwt tokenByValue(String token) {
        return (Jwt) jwtRepository.findByValeur(token).orElseThrow(() -> new RuntimeException("Token invalid"));
    }

 //logout
    public void deconex() {
        Account sub= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findBytoken(
                sub.getEmail(), false, false).orElseThrow(() -> new RuntimeException(" invalid Token"));
        log.info("deconex: " + jwt.getValeur());
        this.disableToken(sub);
        //jwtRepository.deleteAllByValeur(jwt.getValeur());


    }

    @Scheduled(cron = "@daily")
    public void removeUselessToken(){
        log.info("suprresion Token invalid{}", Instant.now());
        this.jwtRepository.deleteAllByExpirationAndDesactive(true,true);
    }

    public void  disableToken(Account subscriber){
        final List<com.example.gestionetatcivil.Entities.Jwt> jwtList = this.jwtRepository.findByAbonne(subscriber.getEmail()).
                peek(
                        jwt -> {
                            jwt.setDesactive(true);
                            jwt.setExpiration(true);
                        }
                ).toList();
        this.jwtRepository.saveAll(jwtList);

    }
}
