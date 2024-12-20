package com.example.hackmehust.redis.api;

import redis.clients.jedis.UnifiedJedis;

public class ProductionUsage {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
        String jsonObject = "{\"name\":\"John\", \"age\":30}";
        jedis.jsonSet("user:1000", jsonObject);
    }
}
