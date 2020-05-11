package com.github.srinithamizh.employee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Employee {

    @Getter
    @Setter
    @NotNull
    private String employeeId;

    @Getter
    @Setter
    @NotNull
    private String employeeName;

    @Getter
    @Setter
    @NotNull
    private String employeeDesignation;

    public Employee(){}

    public Employee(String employeeId, String employeeName, String employeeDesignation) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDesignation = employeeDesignation;
    }
}
