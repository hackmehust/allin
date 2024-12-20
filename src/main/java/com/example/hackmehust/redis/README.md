# Jedis
Link tài liệu tham khảo: 
https://redis.io/docs/latest/develop/clients/jedis/

Sử dụng thêm dependency vào:

Maven
```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>5.2.0</version>
</dependency>
```

Gradle:

```groovy
repositories {
    mavenCentral()
}
//...
dependencies {
    implementation 'redis.clients:jedis:5.2.0'
    //...
}
```

Có 2 cách cấu hình redis:
- Standalone Mode
- Cluster Mode


Redis Cluster: là một cụm máy chủ Redis hoạt động cùng nhau để cung cấp khả năng phân tán và độ khả dụng cao.
- Được cấu thành từ nhiều node.
- JedisCluster
- Cần ít nhất 6 instance (3 master, 3 slave) để đảm bảo tính khả dụng và dự phòng.

## Jedis và Lettuce 
đều là các client Java phổ biến dùng để tương tác với Redis

Jedis: blocking chấp nhận 1 thread

Lettuce: Non-blocking, Asynchronous


### Redis OM Spring

```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
  </repository>
</repositories>
```

```groovy
repositories {
    maven {
        url "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    }
}
```

