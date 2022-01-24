package com.example.commonContext.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Context")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonContext implements Serializable {
    @Id
    private String token;
}
