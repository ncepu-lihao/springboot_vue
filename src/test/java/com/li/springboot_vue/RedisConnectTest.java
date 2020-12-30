package com.li.springboot_vue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class RedisConnectTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //测试是否能连接到redis
    @Test
    void testRedisConnect(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());
    }
    //测试redistemplate写入redis
    @Test
    void testRedisTemplate(){
        stringRedisTemplate.opsForValue().set("name","zhangsan");
    }
    //Redis中get值中文显示为\xe4\xbd\xa0\xe5\xa5\xbd的16进制字符串怎么解决
    //https://blog.csdn.net/tritoy/article/details/81210291
}
