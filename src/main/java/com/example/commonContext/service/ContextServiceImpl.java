package com.example.commonContext.service;

import com.example.commonContext.entity.CommonContext;
import com.example.commonContext.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.exceptions.JedisDataException;

import java.lang.reflect.Field;

@Service
public class ContextServiceImpl<V> implements ContextService<V> {

    private final ContextRepository repository;

    @Autowired
    public ContextServiceImpl( /*@Qualifier("redisRepo")*/ ContextRepository contextRepository) {
        this.repository = contextRepository;
    }

    @Override
    public void createContext(String token, CommonContext context) {
        repository.createContext(token, context);
    }

    @Override
    public void deleteContext(String token) {
        repository.deleteContext(token);
    }

    @Override
    public void createContextField(String fieldName, V fieldValue, String token){
        refreshTimeToLive(token);
        CommonContext context = repository.getContext(token);
        Field field = ReflectionUtils.findField(CommonContext.class, fieldName);
        if (field != null && ReflectionUtils.getField(field,context)==null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, context, fieldValue);
            field.setAccessible(false);
            repository.updateContext(context,token);
        }else{
            throw new JedisDataException("field with current name doesn`t exists or object of current field exists");
        }
    }

    @Override
    public V getContextField(String token, String fieldName) {
        refreshTimeToLive(token);
        CommonContext context = repository.getContext(token);
        Field field = ReflectionUtils.findField(CommonContext.class, fieldName);
        V value = null;
        try{
           value = (V)ReflectionUtils.getField(field,context);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void updateContextField(String fieldName, V fieldValue, String token) {
        refreshTimeToLive(token);
        CommonContext context = repository.getContext(token);
        Field field = ReflectionUtils.findField(CommonContext.class, fieldName);
        if (field != null && ReflectionUtils.getField(field,context)!=null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, context, fieldValue);
            field.setAccessible(false);
            repository.updateContext(context,token);
        }else{
            throw new JedisDataException("field with current name doesn`t exists or object of current field exists doesn`t exists");
        }
    }

    @Override
    public void deleteContextField(String fieldName, V fieldValue, String token) {
        refreshTimeToLive(token);
        CommonContext context = repository.getContext(token);
        Field field = ReflectionUtils.findField(CommonContext.class, fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, context, fieldValue);
            field.setAccessible(false);
            repository.updateContext(context,token);
        }else{
            throw new JedisDataException("field with current name doesn`t exists");
        }
    }

    @Override
    public void refreshTimeToLive(String token) {
        repository.refreshTimeToLive(token);
    }

}
