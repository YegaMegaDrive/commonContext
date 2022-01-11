package com.example.commonContext.repository;

import com.example.commonContext.entity.CommonContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HashContextRepositoryImpl implements ContextRepository{

    private HashOperations<String, Object, Object> hashOperations;
    public static final String KEY = "Context";

    @Autowired
    public HashContextRepositoryImpl(RedisTemplate<String,?> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void createContext(String token,CommonContext context) {
        hashOperations.put(KEY, token, context);
        log.info(String.format("Context with TOKEN %s saved", token));
    }

    public CommonContext getContext(String token) {
        return (CommonContext) hashOperations.get(KEY, token);
    }

    public void updateContext(CommonContext context, String token) {
        hashOperations.put(KEY, token, context);
        log.info(String.format("Context with TOKEN %s updated", context.getToken()));
    }

    public void deleteContext(String token) {
        hashOperations.delete(KEY, token);
        log.info(String.format("Context with TOKEN %s deleted", token));
    }

    @Override
    public void refreshTimeToLive(String token) {
        hashOperations.getOperations().expire(token,Duration.ofMinutes(5));
    }

}
