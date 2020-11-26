package com.example.networkcalling.profile;

import com.example.networkcalling.model.Employee;

public interface ProfileContract {
    interface View {
        void showEmployeeProfile(Employee employee);

        void showMessage(String message);

        void showSuccessfulAddedToCompany();
    }

    interface Presenter {
        void getEmployeeDataFromDatabase(long id);

        void getEmployeeData(long id);

        void addToCompanyEmployee();
    }
}