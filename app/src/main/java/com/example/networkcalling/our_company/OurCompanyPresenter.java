package com.example.networkcalling.our_company;

import android.content.Context;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.repository.our_company_employees.IOurCompanyRepository;
import com.example.networkcalling.repository.our_company_employees.OurCompanyRepository;

import java.util.List;

public class OurCompanyPresenter implements OurCompanyContract.Presenter {

    private IOurCompanyRepository repository;
    private OurCompanyContract.View view;

    public OurCompanyPresenter(OurCompanyContract.View view, Context context) {
        this.view = view;
        repository = new OurCompanyRepository(context);
    }

    @Override
    public void getOurCompanyInfo() {
        List<Employee> employees = repository.getEmployees();
        //if (employees == null) {
        //     view.showMessage("Нет данных в БД. Добавьте сотрудников в штат.");
        // } else {
        view.showEmployees(employees);
        //}
    }

    @Override
    public void editEmployee(Employee employee) {
        repository.updateEmployee(employee);
        getOurCompanyInfo();
    }

    @Override
    public void addEmployee(Employee employee) {
        repository.insertEmployeeWithoutId(employee);
        getOurCompanyInfo();
    }

    @Override
    public void deleteFromOurCompanyEmployee(long id) {
        repository.deleteConcreteEmployee(id);
        getOurCompanyInfo();
    }
}
