package com.example.networkcalling.main;

import android.content.Context;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeesResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.repository.all_employees.AllEmployeesRepository;
import com.example.networkcalling.repository.all_employees.IAllEmployeesRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter, SingleObserver<EmployeesResponse> {

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
        appApiClient.getEmployees()
                // only for demonstration chain
                .doOnSuccess(employeesResponse -> appApiClient.deleteEmployee(1).subscribe())
                // subscribeOn - планировщик наших событий
                .subscribeOn(Schedulers.io())
                // observeOn - мы говорим цепочке событий выделить отдельный поток для обработки этих данных
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull EmployeesResponse employeesResponse) {
        List<Employee> employeeList = employeesResponse.getEmployees();
        repository.deleteAllRows();
        for (Employee employee : employeeList) {
            repository.insertEmployee(employee);
        }
        view.showEmployees(employeeList);
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }
}
