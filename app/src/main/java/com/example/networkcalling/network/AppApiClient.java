package com.example.networkcalling.network;

import com.example.networkcalling.model.EmployeeDeleteResponse;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.model.EmployeesResponse;

import retrofit2.Call;

public class AppApiClient {

    static AppApiClient instance = new AppApiClient();

    public static AppApiClient get() {
        return instance;
    }

    public Call<EmployeesResponse> getEmployees() {
        return ServiceGenerator.getApiService()
                .getEmployees();
    }

    public Call<EmployeeResponse> getEmployee(long id) {
        return ServiceGenerator.getApiService()
                .getEmployee(id);
    }

    public Call<EmployeeDeleteResponse> deleteEmployee(long id) {
        return ServiceGenerator.getApiService()
                .deleteEmployee(id);
    }
}
