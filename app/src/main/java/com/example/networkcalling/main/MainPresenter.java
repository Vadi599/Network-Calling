package com.example.networkcalling.main;

import android.content.Context;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeeDeleteResponse;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.model.EmployeesResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.repository.all_employees.IAllEmployeesRepository;
import com.example.networkcalling.repository.all_employees.AllEmployeesRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private AppApiClient appApiClient = AppApiClient.get();
    private IAllEmployeesRepository repository;
    private MainContract.View view;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        repository = new AllEmployeesRepository(context);
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
                appApiClient.deleteEmployee(1).enqueue(new Callback<EmployeeDeleteResponse>() {
                    @Override
                    public void onResponse(Call<EmployeeDeleteResponse> call, Response<EmployeeDeleteResponse> response) {
                        appApiClient.getEmployee(1).enqueue(new Callback<EmployeeResponse>() {
                            @Override
                            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {

                            }

                            @Override
                            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<EmployeeDeleteResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<EmployeesResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });
    }

}
