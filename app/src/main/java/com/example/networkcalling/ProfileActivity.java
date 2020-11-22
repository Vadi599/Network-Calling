package com.example.networkcalling;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.databinding.ActivityProfileBinding;
import com.example.networkcalling.main.MainActivity;
import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeeResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.repository.DBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private AppApiClient appApiClient = AppApiClient.get();
    private ActivityProfileBinding binding;

    private DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong(MainActivity.PARAM_EMPLOYEE_ID);
            if (isNetworkConnected()) {
                getEmployeeData(id);
            } else {
                getEmployeeDataFromDatabase(id);
            }
        }
    }

    private void getEmployeeDataFromDatabase(long id) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from " + DBHelper.TABLE_NAME + " where id = ?", new String[]{String.valueOf(id)});
        boolean isExistDatabaseRecords = cursor.moveToFirst();
        if (isExistDatabaseRecords) {
            int idColIndex = cursor.getColumnIndex("id");
            int employeeNameIndex = cursor.getColumnIndex("employeeName");
            int employeeSalaryIndex = cursor.getColumnIndex("employeeSalary");
            int employeeAgeIndex = cursor.getColumnIndex("employeeAge");
            long employeeId = cursor.getLong(idColIndex);
            String employeeName = cursor.getString(employeeNameIndex);
            String employeeSalary = cursor.getString(employeeSalaryIndex);
            String employeeAge = cursor.getString(employeeAgeIndex);
            Employee employee = new Employee(employeeId, employeeName, employeeSalary, employeeAge);
            showEmployeeProfile(employee);
        }
    }

    private void getEmployeeData(long id) {
        // Асинхронный способ
        appApiClient.getEmployee(id).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                EmployeeResponse employeeResponse = response.body();
                showEmployeeProfile(employeeResponse.getEmployee());
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

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

    private void showEmployeeProfile(Employee employee) {
        binding.tvName.setText(employee.getEmployeeName());
        binding.tvSalary.setText(employee.getEmployeeSalary());
        binding.tvAge.setText(employee.getEmployeeAge());
        // visibility flags
        // VISIBLE - view видимо
        // INVISIBLE - view не видимо, но контент остается на макете
        // GONE - view не видимо, контент исчезает из макета

        binding.progressView.setVisibility(View.GONE);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
