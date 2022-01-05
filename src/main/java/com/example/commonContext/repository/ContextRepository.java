package com.example.commonContext.repository;

import com.example.commonContext.entity.CommonContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContextRepository {

    private HashOperations<String, Object, Object> hashOperations;
    public static final String KEY = "Context";

    public ContextRepository(RedisTemplate<String,?> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void createContext(String token,CommonContext context) {
        hashOperations.put(KEY, context.getToken(), context);
        log.info(String.format("Context with TOKEN %s saved", context.getToken()));
    }

    public CommonContext getContext(String token) {
        return (CommonContext) hashOperations.get(KEY, token);
    }

   /* public Map<Object, Object> getAll(){
        return hashOperations.entries(KEY);
    }*/

    public void updateContext(CommonContext context) {
        hashOperations.put(KEY, context.getToken(), context);
        log.info(String.format("Context with TOKEN %s updated", context.getToken()));
    }

    public void deleteContext(String token) {
        hashOperations.delete(KEY, token);
        log.info(String.format("Context with TOKEN %s deleted", token));
    }
}
