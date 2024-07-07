package com.virtualize.varejo_da_sorte.api.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.virtualize.varejo_da_sorte.api.configuration.security.to.UserTO;
import com.virtualize.varejo_da_sorte.api.interceptors.LoggingInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TokenAuthenticationService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final String HEADER_IP_REMOTO = "X-Forwarded-For";
    private static final String VAZIO = "";
    private static final String ROLE = "ROLE_";
    private static final String UTF_8 = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;

    public Authentication getAuthentication(HttpServletRequest request) throws UnsupportedEncodingException {

        try {
            String token = request.getHeader(HEADER_STRING);

            if (token != null) {

                String tokenClean = token.replace(TOKEN_PREFIX, VAZIO);
                // faz parser do token
                JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8))).withIssuer(issuer)
                        .build();

                DecodedJWT decodedJwt = verifier.verify(tokenClean);
                if (decodedJwt != null) {
                    return buildUserAuthentication(decodedJwt, request);
                }
            }
            return null;
        } catch (TokenExpiredException exception) {
            LOGGER.error("Token JWT expirado. {}. Retornando null", exception);
            return null;

        } catch (Exception exception) {
            LOGGER.error("Erro no processo de autenticação via token JWT: {}. Retornando null", exception);
            return null;
        }
    }

    private UsernamePasswordAuthenticationToken buildUserAuthentication(DecodedJWT decodedJwt, HttpServletRequest request) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> permissions = decodedJwt.getClaim("permissions").asList(String.class);
        List<String> roles = decodedJwt.getClaim("roles").asList(String.class);

        if (roles != null) {
            roles.stream().map(s -> {
                s = s.replace(ROLE, VAZIO);
                return new SimpleGrantedAuthority(ROLE + s);
            }).forEach(grantedAuthorities::add);
        }

        if (permissions != null) {
            permissions.stream().map(s -> {
                s = s.replace(ROLE, VAZIO);
                return new SimpleGrantedAuthority(s);
            }).forEach(grantedAuthorities::add);
        }

        UserTO details = new UserTO();
        details.setCpf(decodedJwt.getClaim("cpf").asLong());
        details.setOl(decodedJwt.getClaim("ol").asString());

        String ip = decodedJwt.getClaim("ip").asString();
        if (ip != null && !ip.isEmpty()) {
            details.setIpRemoto(ip);
        } else {
            details.setIpRemoto(getClientRemoteIp(request));
        }

        request.setAttribute(LoggingInterceptor.REQUEST_JWT_GRANTED_AUTHORITIES_ATTR_NAME, grantedAuthorities);
        request.setAttribute(LoggingInterceptor.REQUEST_JWT_LOGIN_ATTR_NAME, details.getCpf());
        request.setAttribute(LoggingInterceptor.REQUEST_JWT_IP_ATTR_NAME, ip);

        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(decodedJwt.getSubject(),
                decodedJwt, grantedAuthorities);
        user.setDetails(details);

        return user;
    }

    private String getClientRemoteIp(HttpServletRequest request) {

        String ipsList = request.getHeader(HEADER_IP_REMOTO);

        String ipClient = null;

        // pode haver mais de um ip, sendo o primeiro o ip do cliente
        // e os demais sendo ips de proxys
        // https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Headers/X-Forwarded-For
        if (ipsList != null && !ipsList.isEmpty()) {
            ipClient = ipsList.split(",")[0];
        }

        return ipClient;
    }
}