package com.example.hackmehust.redis.api;

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.Path2;
import redis.clients.jedis.search.*;
import redis.clients.jedis.search.aggr.*;
import redis.clients.jedis.search.schemafields.*;
import org.json.JSONObject;
import java.util.List;

public class JsonQuery {
    public static void main(String[] args) {

        // Connect to the database:
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        // Create some test data to add to the database:
        JSONObject user1 = new JSONObject()
                .put("name", "Paul John")
                .put("email", "paul.john@example.com")
                .put("age", 42)
                .put("city", "London");

        JSONObject user2 = new JSONObject()
                .put("name", "Eden Zamir")
                .put("email", "eden.zamir@example.com")
                .put("age", 29)
                .put("city", "Tel Aviv");

        JSONObject user3 = new JSONObject()
                .put("name", "Paul Zamir")
                .put("email", "paul.zamir@example.com")
                .put("age", 35)
                .put("city", "Tel Aviv");

        // Create an index. In this example, only JSON documents with the key prefix user: are indexed
        SchemaField[] schema = {
                TextField.of("$.name").as("name"),
                TextField.of("$.city").as("city"),
                NumericField.of("$.age").as("age")
        };

        /*
        FT.CREATE yêu cầu Redis được cài đặt module RediSearch, nhưng Redis tiêu chuẩn (standalone) không bao gồm module này.
        Để sử dụng lệnh này, bạn cần sử dụng Redis Stack hoặc tự cài đặt module RediSearch vào Redis.

        docker run -d --name redis-stack -p 6379:6379 redis/redis-stack:latest

        redis-cli MODULE LIST

        Jedis >= 4.2.0 là cần thiết để hỗ trợ các module của Redis Stack.

        Kiểm tra thủ công redis-cli:
        FT.CREATE idx:users ON JSON PREFIX 1 user: SCHEMA $.name AS name TEXT $.city AS city TEXT $.age AS age NUMERIC


        Update redis trên docker hiện tại:

        - docker stop <container_name>
        - docker run -d --name redis-stack -p 6379:6379 redis/redis-stack:latest
         */

        // Tạo một chỉ mục (index) trong Redis Stack với module RediSearch
//        String createResult = jedis.ftCreate("idx:users",
//                FTCreateParams.createParams()
//                        .on(IndexDataType.JSON)
//                        .addPrefix("user:"),
//                schema // Các cột đánh index
//        );

//        System.out.println(createResult); // >>> OK

        String user1Set = jedis.jsonSet("user:1", new Path2("$"), user1);
        String user2Set = jedis.jsonSet("user:2", new Path2("$"), user2);
        String user3Set = jedis.jsonSet("user:3", new Path2("$"), user3);


        // "Paul" in any field and have an age value in the range 30 to 40
        // name, city, age
        SearchResult findPaulResult = jedis.ftSearch("idx:users",
                "Paul @age:[30 40]"
        );

        System.out.println(findPaulResult.getTotalResults()); // >>> 1

        List<Document> paulDocs = findPaulResult.getDocuments();

        for (Document doc: paulDocs) {
            System.out.println(doc.getId());
        }
        // >>> user:3

        // Specify query options to return only the city field:
        SearchResult citiesResult = jedis.ftSearch("idx:users",
                "Paul",
                FTSearchParams.searchParams()
                        .returnFields("city")
        );

        System.out.println(citiesResult.getTotalResults()); // >>> 2

        for (Document doc: citiesResult.getDocuments()) {
            System.out.println(doc.getId());
        }
        // >>> user:1
        // >>> user:3


        // Use an aggregation query to count all users in each city.
        AggregationResult aggResult = jedis.ftAggregate("idx:users",
                new AggregationBuilder("*")
                        .groupBy("@city", Reducers.count().as("count"))
        );

        System.out.println(aggResult.getTotalResults()); // >>> 2

        for (Row cityRow: aggResult.getRows()) {
            System.out.println(String.format(
                    "%s - %d",
                    cityRow.getString("city"), cityRow.getLong("count"))
            );
        }
        // >>> London - 1
        // >>> Tel Aviv - 2

        // Có thể xem thêm Redis query engine
        // https://redis.io/docs/latest/develop/interact/search-and-query/
    }
}
