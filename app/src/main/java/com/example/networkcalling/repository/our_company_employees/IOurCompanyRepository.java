package com.example.networkcalling.repository.our_company_employees;

import com.example.networkcalling.model.Employee;

import java.util.List;

public interface IOurCompanyRepository {

    List<Employee> getEmployees();

    Employee getEmployee(long id);

    void deleteAllRows();

    void deleteConcreteEmployee(long id);

    void insertEmployee(Employee employee);

    void updateEmployee(Employee employee);

}
