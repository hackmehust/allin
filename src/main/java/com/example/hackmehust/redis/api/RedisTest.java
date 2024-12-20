package com.example.hackmehust.redis.api;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {
    public static void main(String[] args) {
//        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
//
//        // Code that interacts with Redis...
//        String res1 = jedis.set("bike:1", "Deimos");
//        System.out.println(res1); // OK
//
//        String res2 = jedis.get("bike:1");
//        System.out.println(res2); // Deimos
//
//        jedis.close();

        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));
//        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
//        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7380));
        JedisCluster jedis = new JedisCluster(jedisClusterNodes);

    }
}
