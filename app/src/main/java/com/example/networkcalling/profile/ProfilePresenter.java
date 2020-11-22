package com.example.networkcalling.profile;

import android.content.Context;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.repository.IRepository;
import com.example.networkcalling.repository.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {
    private AppApiClient appApiClient = AppApiClient.get();
    private IRepository repository;
    private ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        repository = new Repository(context);
    }

    @Override
    public void getEmployeeDataFromDatabase(long id) {
        Employee employee = repository.getEmployee(id);
        if (employee == null) {
            view.showMessage("Пользователь не найден. ID = " + id);
        } else {
            view.showEmployeeProfile(employee);
        }
    }

    @Override
    public void getEmployeeData(long id) {
        // Асинхронный способ
        appApiClient.getEmployee(id).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                EmployeeResponse employeeResponse = response.body();
                view.showEmployeeProfile(employeeResponse.getEmployee());
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });

        /*// Синхронный
        try {
            Response<EmployeeResponse> response = appApiClient.getEmployee(id).execute();
            EmployeeResponse employeeResponse = response.body();
            showEmployeeProfile(employeeResponse.getEmployee());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
