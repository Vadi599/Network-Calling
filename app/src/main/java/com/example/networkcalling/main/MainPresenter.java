package com.example.networkcalling.main;

import android.content.Context;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeesResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.repository.IRepository;
import com.example.networkcalling.repository.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private AppApiClient appApiClient = AppApiClient.get();
    private IRepository repository;
    private MainContract.View view;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        repository = new Repository(context);
    }

    @Override
    public void getDataFromDatabase() {
        List<Employee> employees = repository.getEmployees();
        if (employees == null) {
            view.showMessage("Нет данных в БД. Включите интернет и перезапустите приложение");
        } else {
            view.showEmployees(employees);
        }
    }

    @Override
    public void getDataFromServer() {
        appApiClient.getEmployees().enqueue(new Callback<EmployeesResponse>() {
            @Override
            public void onResponse(Call<EmployeesResponse> call, Response<EmployeesResponse> response) {
                EmployeesResponse employeesResponse = response.body();
                List<Employee> employeeList = employeesResponse.getEmployees();
                repository.deleteAllRows();
                for (Employee employee : employeeList) {
                    repository.insertEmployee(employee);
                }
                view.showEmployees(employeeList);
            }

            @Override
            public void onFailure(Call<EmployeesResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });
    }

}
