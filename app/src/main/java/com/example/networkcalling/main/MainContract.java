package com.example.networkcalling.main;

import com.example.networkcalling.model.Employee;

import java.util.List;

public interface MainContract {

    interface View {
        void showEmployees(List<Employee> employees);

        void showConcreteEmployee(Employee employee);

        void showMessage(String message);
    }

    interface Presenter {
        void getDataFromDatabase();

        void getDataFromServer();
    }

}
