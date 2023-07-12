package org.algotithmcontestdatacollect.displaybackend.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtil {
    Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    @Value("${jwt.secret}")
    public String secret;
    @Value("${jwt.expire}")
    public int expire;

    @Value("${jwt.issuer}")
    public String issuer;

    public Map<String, Object> HEADER_MAP = new HashMap<>()
    {
        {
            put("alg", "HS256");
            put("typ", "JWT");
        }
    };


    public String createToken(Map<String,String> claimMap) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire);
        var builder =  JWT.create();
        for (var entity : claimMap.entrySet()) {
            builder.withClaim(entity.getKey(),entity.getValue());
        }
        builder.withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .withIssuer(issuer)
                .withHeader(HEADER_MAP);
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public boolean checkToken(HttpServletRequest request){
        var token = request.getHeader("token");
        var verify = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build();
        var decodeJWT = verify.verify(token);
        var clains = decodeJWT.getClaims();
        for(var entity : clains.entrySet()) {
            request.setAttribute(entity.getKey(),entity.getValue().asString());
        }
        return true;
    }
}
