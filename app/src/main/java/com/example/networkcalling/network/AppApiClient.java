package com.example.networkcalling.network;

import com.example.networkcalling.model.EmployeeDeleteResponse;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.model.EmployeesResponse;

import io.reactivex.Single;
import retrofit2.Call;

public class AppApiClient {

    static AppApiClient instance = new AppApiClient();

    public static AppApiClient get() {
        return instance;
    }

    public Single<EmployeesResponse> getEmployees() {
        return ServiceGenerator.getApiService()
                .getEmployees();
    }

    public Single<EmployeeResponse> getEmployee(long id) {
        return ServiceGenerator.getApiService()
                .getEmployee(id);
    }

    public Single<EmployeeDeleteResponse> deleteEmployee(long id) {
        return ServiceGenerator.getApiService()
                .deleteEmployee(id);
    }
}
