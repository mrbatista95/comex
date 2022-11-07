package br.com.alura.comex.security;

import br.com.alura.comex.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {

    @Value("${api.jwt.expiracao}")
    private long expiracao;

    @Value("${api.jwt.secret}")
    private String secret;
    public String gerarToken(Authentication authenticate) {

        var hoje = new Date();

        var dataDeExpiracaoEmMills = hoje.toInstant()
                .plusMillis(expiracao)
                .toEpochMilli();

        var dataExpiracao = new Date(dataDeExpiracaoEmMills);

        var usuario = (Usuario) authenticate.getPrincipal();

        return  Jwts.builder()
                .setIssuer("API alura comex")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setIssuedAt(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUsuarioId(String token) {
        var clains = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(clains.getSubject());
    }
}