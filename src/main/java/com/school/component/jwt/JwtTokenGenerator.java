//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.jwt;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.exception.JwtExpiredAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JwtTokenGenerator {
    private JwtPayloadBuilder jwtPayloadBuilder;
    private JwtProperties jwtProperties;
    private KeyPair keyPair;

    public JwtTokenGenerator(JwtPayloadBuilder jwtPayloadBuilder, JwtProperties jwtProperties) {
        this.jwtPayloadBuilder = jwtPayloadBuilder;
        this.jwtProperties = jwtProperties;
        KeyPairFactory keyPairFactory = new KeyPairFactory();
        this.keyPair = keyPairFactory.create(jwtProperties.getKeyLocation(), jwtProperties.getKeyAlias(), jwtProperties.getKeyPass());
    }

    public JwtTokenPair jwtTokenPair(String audience, Set<String> authorities, Map<String, String> additional) throws JsonProcessingException {
        String accessToken = this.jwtToken(audience, this.jwtProperties.getTokenExpirationDays(), authorities, additional);
        String refreshToken = this.jwtToken(audience, this.jwtProperties.getRefreshTokenExpirationDays(), authorities, additional);
        JwtTokenPair jwtTokenPair = new JwtTokenPair(accessToken, refreshToken);
        return jwtTokenPair;
    }

    public JwtTokenPair jwtTokenPair(String audience, Collection<? extends GrantedAuthority> authorities, Map<String, String> additional) throws JsonProcessingException {
        Set<String> authorityStrings = new HashSet();
        Iterator var5 = authorities.iterator();

        while (var5.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority) var5.next();
            authorityStrings.add(authority.getAuthority());
        }

        return this.jwtTokenPair(audience, authorityStrings, additional);
    }

    private String jwtToken(String audience, int tokenExpirationDays, Set<String> authorities, Map<String, String> additional) throws JsonProcessingException {
        String payload = this.jwtPayloadBuilder.issuer(this.jwtProperties.getIssuer()).subscriber(this.jwtProperties.getSubscriber()).audience(audience).additional(additional).authorities(authorities).expirationDays(tokenExpirationDays).builder();
        RSAPrivateKey privateKey = (RSAPrivateKey) this.keyPair.getPrivate();
        RsaSigner signer = new RsaSigner(privateKey);
        return JwtHelper.encode(payload, signer).getEncoded();
    }

    public JSONObject decodeAndVerify(String jwtToken) {
        Assert.hasText(jwtToken, "jwtToken should not be null!");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();
        SignatureVerifier signatureVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = null;
        jwt = JwtHelper.decodeAndVerify(jwtToken, signatureVerifier);
        String claims = jwt.getClaims();
        JSONObject jsonObject = JSONObject.parseObject(claims);
        String expiration = (String) jsonObject.get("expiration");
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(expiration, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            throw new JwtExpiredAuthenticationException("jwt has been expired!");
        } else {
            return jsonObject;
        }
    }
}
