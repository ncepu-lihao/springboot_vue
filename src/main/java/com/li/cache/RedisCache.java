package com.li.cache;

import com.li.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 因为RedisCache对象由mybatis进行实例化，并不是spring容器中的bean，不能通过aotowrie的方式引用
 * redisTemplate,需要手动去调用容器的getBean方法
 */
@Slf4j
public class RedisCache implements Cache {
    private String id;
    //通过构造函数将namespqce下的类名作为id传进来
    public RedisCache(String id) {
        log.info("缓存id[{}]",id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override//放入redis缓存
    public void putObject(Object key, Object value) {
        log.info("加入缓存的key为[{}],Value为[{}]",key.toString(),value);
        getRedisTemplate().opsForHash().put(id,key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        log.info("获取缓存的key为[{}]",key.toString());
       return getRedisTemplate().opsForHash().get(id,key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override//清除缓存
    public void clear() {
        log.info("清除缓存信息");
        //opsForHash删除指定一个
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        //intValue:Long转int
        return getRedisTemplate().opsForHash().size(id).intValue();
    }
    //通过工厂获取redistemplate
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationContextUtils.getApplicationContext("redisTemplate");
        //将key的序列化方式调整成string ，value仍然是jdk的序列话方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
