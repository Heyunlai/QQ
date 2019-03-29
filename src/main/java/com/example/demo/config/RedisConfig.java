package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        //创建Redis操作对象，用来完成数据库操作
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<String ,Object>();
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //告诉Redis操作对象，value做序列化保存
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        //告诉Redis对象，连接的端口，主机
        redisTemplate.setConnectionFactory(connectionFactory);
        //返回以供其他地方使用
        return redisTemplate;
    }

}
