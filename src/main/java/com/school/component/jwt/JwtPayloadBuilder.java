//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.jwt;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class JwtPayloadBuilder {
    private Map<String, String> payload = new HashMap();
    private Map<String, String> additional;
    private String issuer;
    private String subscriber;
    private String audience;
    private LocalDateTime expiration;
    private LocalDateTime issueAt;
    private Set<String> roles = new HashSet();
    private String jti;

    public JwtPayloadBuilder() {
    }

    public JwtPayloadBuilder issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public JwtPayloadBuilder subscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public JwtPayloadBuilder audience(String audience) {
        this.audience = audience;
        return this;
    }

    public JwtPayloadBuilder authorities(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public JwtPayloadBuilder expirationDays(int days) {
        Assert.isTrue(days > 0, "expiration days should be positive!");
        this.issueAt = LocalDateTime.now();
        this.expiration = this.issueAt.plusDays((long)days);
        return this;
    }

    public String builder() throws JsonProcessingException {
        this.issueAt = LocalDateTime.now();
        this.payload.put("issuer", this.issuer);
        this.payload.put("subscriber", this.subscriber);
        this.payload.put("audience", this.audience);
        this.payload.put("expiration", this.expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.payload.put("issueAt", this.issueAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.payload.put("roles", JSONObject.toJSONString(this.roles));
        if (!CollectionUtils.isEmpty(this.additional)) {
            this.payload.putAll(this.additional);
        }

        return JSONObject.toJSONString(this.payload);
    }
}
