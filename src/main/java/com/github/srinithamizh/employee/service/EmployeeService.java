package com.github.srinithamizh.employee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.srinithamizh.employee.Logic;
import com.github.srinithamizh.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.github.srinithamizh.employee.Logic.toByteArray;

@Repository
public class EmployeeService {

    @Autowired
    private Logic logic;

    @Value("${amazonProperties.bucketName}")
    public String employeeBucket;

    public Employee getEmployee(String employeeId) throws IOException {
        byte[] employee = logic.getEmployeeById(employeeBucket, employeeId);
        ObjectMapper objectMapper = new ObjectMapper();
        if(employee != null) {
            return objectMapper.readValue(employee, Employee.class);
        }
        return null;
    }

    public Employee addEmployee(Employee employee)  {
        byte[] employeeByte = toByteArray(employee);
        logic.saveEmployee(employeeBucket,employee.getEmployeeId(),employeeByte);
        return employee;
    }

}
