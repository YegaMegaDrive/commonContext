package com.example.commonContext.repository;

import com.example.commonContext.entity.CommonContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository("redisRepo")
@Primary
@RequiredArgsConstructor
@Slf4j
public class ContextRepositoryImpl implements ContextRepository{

    @Value("$cache.redis.time-to-live" )
    private String expireTime;

    private ValueOperations<String, Object> valueOperations;

    @Autowired
    public ContextRepositoryImpl(RedisTemplate<String,Object> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void createContext(String token, CommonContext context) {
        this.valueOperations.set(token,context);
    }

    @Override
    public CommonContext getContext(String token) {
        return (CommonContext)this.valueOperations.get(token);
    }

    @Override
    public void updateContext(CommonContext context, String token) {
        this.valueOperations.set(token,context, Duration.ofSeconds(Integer.parseInt(expireTime)));
    }

    @Override
    public void deleteContext(String token) {
        this.valueOperations.getAndDelete(token);
    }

    @Override
    public void refreshTimeToLive(String token) {
        this.valueOperations.getAndExpire(token, Duration.ofSeconds(Integer.parseInt(expireTime)));
    }
}
