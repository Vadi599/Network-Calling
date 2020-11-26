package com.example.networkcalling.our_company;

import com.example.networkcalling.model.Employee;

import java.util.List;

public interface OurCompanyContract {

    interface View {
        void showEmployees(List<Employee> employees);

        void showMessage(String message);
    }

    interface Presenter {

        void getOurCompanyInfo();
    }

}
