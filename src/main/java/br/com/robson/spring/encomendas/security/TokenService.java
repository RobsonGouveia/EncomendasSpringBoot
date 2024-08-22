package br.com.robson.spring.encomendas.security;

import br.com.robson.spring.encomendas.business.condominios.domain.Condomino;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
//import lombok.Value;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Condomino condomino){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("encomendas")
                    .withSubject(condomino.getName())
                    .withExpiresAt(this.DataExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro de Autenticação");
        }
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("encomendas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant DataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

//    public String gerarToken(Condomino condomino) {
//            return JWT.create()
//                    .withIssuer("Encomendas")
//                    .withSubject(condomino.getUsername())
//                    .withClaim("id", condomino.getId())
//                    .withExpiresAt(LocalDateTime.now().plusMinutes(10)
//                            .toInstant(ZoneOffset.of("-03:00"))
//                    ).sign(Algorithm.HMAC256("secret"));
//    }
}
