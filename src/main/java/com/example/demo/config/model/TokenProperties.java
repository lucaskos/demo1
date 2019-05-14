package com.example.demo.config.model;

/**
 * Created by Luke on 24.10.2018.
 */
public class TokenProperties {
    private long maxAgeSeconds;
    private String secret;

    public TokenProperties(long maxAgeSeconds, String secret) {
        this.maxAgeSeconds = maxAgeSeconds;
        this.secret = secret;
    }

    public TokenProperties() {
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }
    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public String getSecret() {
        return "test";
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
