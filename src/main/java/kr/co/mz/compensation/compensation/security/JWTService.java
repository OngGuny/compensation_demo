package kr.co.mz.compensation.compensation.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Component
public class JWTService {

    @Value("${private.key.path}")
    private String privateKeyPemPath;
    @Value("${public.key.path}")
    private String publicKeyPemPath;
    @Value("${jwt.expiration}")
    private String expiration;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    private void init() {
        try {
            KeyPair keyPair = KeyPairLoader.loadKeyPair(privateKeyPemPath, publicKeyPemPath);
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to Key Load", e);
        }

    }

    public String generateToken(Authentication authentication) {
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        var seq = customUserDetails.getUserDto().getSeq();
        var email = customUserDetails.getUserDto().getEmail();
        var name = customUserDetails.getUserDto().getName();
        name = name == null ? "Anonymous" : name;

        var currentDate = new Date();
        var expireDate = new Date(currentDate.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setSubject(email)
                .addClaims(Map.of("seq", seq, "email", email, "name", name))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
    
    public String getEmailJWT(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
