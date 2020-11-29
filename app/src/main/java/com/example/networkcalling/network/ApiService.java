package com.example.networkcalling.network;

import com.example.networkcalling.model.EmployeeDeleteResponse;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.model.EmployeesResponse;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("employees")
    Single<EmployeesResponse> getEmployees();

    @GET("employee/{id}")
    Single<EmployeeResponse> getEmployee(@Path("id") long id);

    @DELETE("delete/{id}")
    Single<EmployeeDeleteResponse> deleteEmployee(@Path("id") long id);

}
