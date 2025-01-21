package com.example.service;


import com.example.EcomDevopsApplication;
import com.example.FieldMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EcomDevopsApplication.class)
 class CacheServiceTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache userCache;

    @InjectMocks
    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        // Initialize mock objects
        MockitoAnnotations.openMocks(this);

        // Mock CacheManager to return the userCache
        when(cacheManager.getCache("userCache")).thenReturn(userCache);

        // Mock Cache's put and get methods
        when(userCache.get("AllData")).thenReturn(null);
    }

    @Test
    void testLoadCacheData() throws Exception {
        // Prepare a mock InputStream for the JSON data
        String jsonData = "[{\"EmployeeData\": [" +
                "{\"fieldName\":\"name\",\"sourceField\":\"employee_name\",\"dataType\":\"String\",\"isRequired\":\"true\"}," +
                "{\"fieldName\":\"age\",\"sourceField\":\"employee_age\",\"dataType\":\"Integer\",\"isRequired\":\"false\"}]," +
                "\"DepartmentData\": [" +
                "{\"fieldName\":\"departmentId\",\"sourceField\":\"dept_id\",\"dataType\":\"String\",\"isRequired\":true}," +
                "{\"fieldName\":\"departmentName\",\"sourceField\":\"dept_name\",\"dataType\":\"String\",\"isRequired\":\"false\"}," +
                "{\"fieldName\":\"manager\",\"sourceField\":\"dept_manager\",\"dataType\":\"String\",\"isRequired\":\"true\"}]}]";

        InputStream inputStream = getClass().getResourceAsStream("/data.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, List<FieldMapping>>> data = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

        // Simulate loading data into the cache
        cacheService.loadCacheData();

        // Verify that the cache's 'AllData' key was populated
        verify(userCache, times(1)).put(eq("AllData"), any());

        List<FieldMapping> mockData = List.of(new FieldMapping("name", "employee_name", "String", "true"),
                new FieldMapping("age", "employee_age", "Integer", "false")
                );

        // Mock the cache to return the mock data
        when(userCache.get("EmployeeData")).thenReturn(() -> mockData);
        // Test if the cache data is correctly retrieved
        List<FieldMapping> employeeData = cacheService.getCacheData("EmployeeData");
        assertNotNull(employeeData);
        assertEquals(2, employeeData.size());
    }

    @Test
    void testGetCacheData() {
        // Prepare some mock data
        List<FieldMapping> mockData = List.of(new FieldMapping("name", "employee_name", "String", "true"));

        // Mock the cache to return the mock data
        when(userCache.get("EmployeeData")).thenReturn(() -> mockData);

        // Retrieve data from the cache
        List<FieldMapping> retrievedData = cacheService.getCacheData("EmployeeData");

        // Assert the data is not null and matches the mock data
        assertNotNull(retrievedData);
        assertEquals(1, retrievedData.size());
        assertEquals("employee_name", retrievedData.get(0).getSourceField());
    }

    @Test
    void testGetAllCacheData() {
        // Prepare some mock data
        Map<String, List<FieldMapping>> mockAllData = Map.of(
                "EmployeeData", List.of(new FieldMapping("name", "employee_name", "String", "true"))
        );

        // Mock the cache to return the mock all data
        when(userCache.get("AllData")).thenReturn(() -> mockAllData);

        // Retrieve all cache data
        Map<String, List<FieldMapping>> allData = cacheService.getcacheAllData();

        // Assert that the all data map is not null and contains the expected data
        assertNotNull(allData);
        assertTrue(allData.containsKey("EmployeeData"));
        assertEquals(1, allData.get("EmployeeData").size());
    }
}

