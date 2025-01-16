package com.example.service;

import com.example.FieldMapping;
import com.example.controller.CustomerController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager; // Autowiring CacheManager directly

    private Cache userCache;

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);


    @PostConstruct
    public void init() {
        this.userCache = cacheManager.getCache("userCache");
        loadCacheData();// Initializing userCache after CacheManager is injected
    }

    // Load JSON data into the cache
    public void loadCacheData() {
        logger.info("loadCacheData:: START");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            List<Map<String, List<FieldMapping>>> data = objectMapper.readValue(
                    inputStream, new TypeReference<List<Map<String, List<FieldMapping>>>>() {});
            System.out.println("userCache: "+userCache);
            Map<String, List<FieldMapping>> newMap =new HashMap<>();
            for (Map<String, List<FieldMapping>> map : data) {
                newMap.putAll(map);
                map.forEach((key, value) -> {
                    if (userCache != null) {
                        userCache.put(key, value); // Store data in cache
                    }
                });
            }
            userCache.put("AllData",newMap);

            logger.info("loadCacheData:: getcacheAllData:: {}", getcacheAllData());
            logger.info("loadCacheData:: END");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cache data", e);
        }
    }

    // Retrieve data from the cache by key
    public List<FieldMapping> getCacheData(String key) {
        System.out.println(getcacheAllData());
        if (userCache != null) {
            Cache.ValueWrapper valueWrapper = userCache.get(key);
            if (valueWrapper != null) {
                return (List<FieldMapping>) valueWrapper.get();
            }
        }
        throw new RuntimeException("Key not found in cache: " + key);
    }

    public Map<String, List<FieldMapping>> getcacheAllData() {
        if (userCache != null) {
            Cache.ValueWrapper valueWrapper = userCache.get("AllData");
            if (valueWrapper != null) {
                return (Map<String, List<FieldMapping>>) valueWrapper.get();
            }
        }
        return null;
    }
}
