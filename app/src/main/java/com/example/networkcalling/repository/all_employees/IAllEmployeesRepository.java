package com.example.networkcalling.repository.all_employees;

import com.example.networkcalling.model.Employee;

import java.util.List;

public interface IAllEmployeesRepository {

    List<Employee> getEmployees();

    Employee getEmployee(long id);

    void deleteAllRows();

    void insertEmployee(Employee employee);

}
