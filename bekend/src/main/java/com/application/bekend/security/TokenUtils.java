package com.application.bekend.security;

import com.application.bekend.model.MyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {


    @Value("spring-security-example")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("3000000")
    private Long EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(MyUser user){
        final Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities());
        claims.put("sub", user.getEmail());
        claims.put("created", new Date(System.currentTimeMillis()));
        return Jwts.builder().setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private Date generateExpirationDate(){
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    private String generateAudience(){
        return AUDIENCE_WEB;
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        MyUser user = (MyUser) userDetails;
        final String email = getEmailFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (email != null && email.equals(((MyUser) userDetails).getEmail()) && !isTokenExpired(token));
    }

    public String getEmailFromToken(String token){
        String email;
        try{
            final Claims claims = this.getAllClaimsFromToken(token);
            email = claims.getSubject();
        }catch (Exception e){
            email = null;
        }
        return email;
    }

    public Date getIssuedAtDateFromToken(String token){
        Date issuedAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issuedAt = claims.getIssuedAt();
        }catch (Exception e){
            issuedAt = null;
        }

        return issuedAt;
    }

    public String getAudienceFromToken(String token){
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        }catch (Exception e){
            audience = null;
        }

        return audience;
    }

    public Date getExpirationDateFromToken(String token){
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        }catch (Exception e){
            expiration = null;
        }

        return expiration;
    }

    public Long getExpiredIn(){
        return EXPIRES_IN;
    }

    public String getToken(HttpServletRequest request){
        String authHeader = getAuthHeaderFromHeader(request);

        if(authHeader != null && authHeader.startsWith("X-Auth-Token ")){
            return authHeader.substring(13);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request){
        return request.getHeader(AUTH_HEADER);
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token){
        String audience = this.getAudienceFromToken(token);
        return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
    }

    private Claims getAllClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            claims = null;
        }

        return claims;
    }
}
