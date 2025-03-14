package com.example.controller;

import com.example.FieldMapping;
import com.example.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;


    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }




    @PostMapping("/load")
    public String loadCache() {
        cacheService.loadCacheData();
        return "Cache data loaded successfully";
    }


    @GetMapping("/get/{key}")
    public List<FieldMapping> getCacheData(@PathVariable String key) {
        return cacheService.getCacheData(key);
    }
}
