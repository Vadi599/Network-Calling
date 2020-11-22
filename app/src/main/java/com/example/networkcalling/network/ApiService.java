package com.example.networkcalling.network;

import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.model.EmployeesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("employees")
    Call<EmployeesResponse> getEmployees();

    @GET("employee/{id}")
    Call<EmployeeResponse> getEmployee(@Path("id") long id);

}
