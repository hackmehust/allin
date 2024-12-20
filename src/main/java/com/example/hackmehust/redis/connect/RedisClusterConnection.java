package com.example.hackmehust.redis.connect;

import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import java.util.HashSet;
import java.util.Set;

public class RedisClusterConnection {

    public static void main(String[] args) {
        // Thay thế bằng địa chỉ IP và port của các node trong cluster của bạn
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379)); // Master 1
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6380)); // Master 2
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6381)); // Master 3

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10); // Điều chỉnh tùy theo nhu cầu
        poolConfig.setMaxIdle(5); // Điều chỉnh tùy theo nhu cầu

        JedisCluster jedisCluster = null;
        try {
            jedisCluster = new JedisCluster(jedisClusterNodes, (JedisClientConfig) poolConfig);

            // Set data
            jedisCluster.set("mykey", "myvalue");
            System.out.println("Data pushed successfully!");

            // Get data
            String value = jedisCluster.get("mykey");
            System.out.println("Retrieved value: " + value);


        } catch (JedisConnectionException e) {
            System.err.println("Error connecting to Redis Cluster: " + e.getMessage());
        } finally {
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
    }
}