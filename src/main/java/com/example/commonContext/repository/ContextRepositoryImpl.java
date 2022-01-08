package com.example.commonContext.repository;

import com.example.commonContext.entity.CommonContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContextRepositoryImpl implements ContextRepository{

    private HashOperations<String, Object, Object> hashOperations;
    public static final String KEY = "Context";

    @Autowired
    public ContextRepositoryImpl(RedisTemplate<String,?> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void createContext(String token,CommonContext context) {
        hashOperations.put(KEY, token, context);
        log.info(String.format("Context with TOKEN %s saved", token));
    }

    public CommonContext getContext(String token) {
        return (CommonContext) hashOperations.get(KEY, token);
    }

    public void updateContext(CommonContext context) {
        hashOperations.put(KEY, context.getToken(), context);
        log.info(String.format("Context with TOKEN %s updated", context.getToken()));
    }

    public void deleteContext(String token) {
        hashOperations.delete(KEY, token);
        log.info(String.format("Context with TOKEN %s deleted", token));
    }
}
