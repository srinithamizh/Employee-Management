package com.github.srinithamizh.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.srinithamizh.employee.model.Employee;
import com.github.srinithamizh.employee.service.EmployeeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Employee details not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") String employeeId) throws IOException {
        Employee employee = employeeService.getEmployee(employeeId);
        if(employee!=null){
            return new ResponseEntity<>(employee,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/employee", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Employee successfully created"),
            @ApiResponse(code = 503, message = "Employee couldn't created")})
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee employee) throws JsonProcessingException {
        if(employeeService.addEmployee(employee) != null){
            return new ResponseEntity<>(employee,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(employee,HttpStatus.NOT_IMPLEMENTED);
    }
}
