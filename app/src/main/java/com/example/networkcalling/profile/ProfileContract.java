package com.example.networkcalling.profile;

import com.example.networkcalling.model.Employee;

public interface ProfileContract {
    interface View {
        void showEmployeeProfile(Employee employee);

        void showMessage(String message);
    }

    interface Presenter {
        void getEmployeeDataFromDatabase(long id);

        void getEmployeeData(long id);
    }
}