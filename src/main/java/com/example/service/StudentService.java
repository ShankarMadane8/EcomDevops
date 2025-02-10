package com.example.service;


import com.example.annotation.LogExecutionTime;
import com.example.entity.Address;
import com.example.entity.Student;
import com.example.repository.AddressRepository;
import com.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentService {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;

    private static final Logger logger= LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository,AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
    }


    @LogExecutionTime
    public List<Student> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        logger.info("students :: {}",studentList);
        return studentList;
    }

    @LogExecutionTime
    public List<Address> getAllAddress() {
        List<Address> addressList = addressRepository.findAll();
        logger.info("addresses :: {} ",addressList);
        return addressList;
    }
}

