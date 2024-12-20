package com.example.hackmehust.redis.data_types;

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;

public class RedisString {

    public static void main(String[] args) {


        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

//        String res1 = jedis.set("bike:1", "Deimos");
//        System.out.println(res1); // OK
//        String res2 = jedis.get("bike:1");
//        System.out.println(res2); // Deimos

//        Long res3 = jedis.setnx("bike:1", "bike"); // Nếu key đã tồn tại thì không gán giá trị mới
//        System.out.println(res3); // 0 (because key already exists)
//        System.out.println(jedis.get("bike:1")); // Deimos (value is unchanged)


        // Nếu không tồn tại key thì không action gì và trả về null
//        String res4 = jedis.set("bike:1", "bike", SetParams.setParams().xx());
//        // set the value to "bike" if it already exists
//        System.out.println(res4); // OK

//        String res10 = jedis.set("bike:10", "Thang");
//        String testrung = jedis.set("bike:1", "a");
//
//        String res5 = jedis.mset("bike:1", "Deimos", "bike:2", "Ares", "bike:3", "Vanth");
//        System.out.println(res5); // OK
//        List<String> res6 = jedis.mget("bike:1", "bike:2", "bike:3", "bike:10");
//        System.out.println(res6); // [Deimos, Ares, Vanth]

        jedis.set("total_crashes", "a");
        Long res7 = jedis.incr("total_crashes");
        System.out.println(res7); // 1
        Long res8 = jedis.incrBy("total_crashes", 10);
        System.out.println(res8); // 11
    }
}
