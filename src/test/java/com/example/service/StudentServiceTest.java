package com.example.service;


import com.example.EcomDevopsApplication;
import com.example.entity.Address;
import com.example.entity.Student;
import com.example.repository.AddressRepository;
import com.example.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EcomDevopsApplication.class)
//@ActiveProfiles("test")
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void testGetAllStudents() {
        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(1); // Based on your data.sql
        assertThat(students.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    void testGetAllAddresses() {
        // Act
        List<Address> addresses = studentService.getAllAddress();

        // Assert
        assertThat(addresses).isNotNull();
        assertThat(addresses.size()).isEqualTo(2); // Based on your data.sql
        assertThat(addresses.get(0).getCity()).isEqualTo("New York");
        assertThat(addresses.get(1).getCity()).isEqualTo("Los Angeles");
    }

    @Test
    void testStudentAndAddressRelationship() {
        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(1); // Based on your data.sql
        assertThat(students.get(0).getAddresses()).isNotNull();
        assertThat(students.get(0).getAddresses().size()).isEqualTo(2);
    }
}

