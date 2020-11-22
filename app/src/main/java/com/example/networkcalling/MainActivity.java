package com.example.networkcalling;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.adapter.EmployeesAdapter;
import com.example.networkcalling.databinding.ActivityMainBinding;
import com.example.networkcalling.model.Employee;
import com.example.networkcalling.model.EmployeesResponse;
import com.example.networkcalling.network.AppApiClient;
import com.example.networkcalling.network.DBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String PARAM_EMPLOYEE_ID = "MainActivity.EMPLOYEE_ID";
    private AppApiClient appApiClient = AppApiClient.get();
    private ActivityMainBinding binding;
    private EmployeesAdapter adapter;
    private DBHelper dbHelper;
    private final String EMPLOYEE_NAME = "employeeName";
    private final String EMPLOYEE_SALARY = "employeeSalary";
    private final String EMPLOYEE_AGE = "employeeAge";
    private final String EMPLOYEE_ID = "id";
    SQLiteDatabase db;
    boolean isExistDatabaseRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new EmployeesAdapter();
        binding.rvEmployees.setAdapter(adapter);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        adapter.setOnItemClickListener(this::showConcreteEmployee);
        if (isNetworkConnected()) {
            getDataFromServer();
        } else {
            Toast.makeText(this, "Нет инета тянем из БД", Toast.LENGTH_SHORT).show();
            getDataFromDatabase();
        }
    }

    private void getDataFromDatabase() {
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        isExistDatabaseRecords = cursor.moveToFirst();
        if (isExistDatabaseRecords) {
            ArrayList<Employee> employees = new ArrayList<>();
            int idColIndex = cursor.getColumnIndex("id");
            int employeeNameIndex = cursor.getColumnIndex("employeeName");
            int employeeSalaryIndex = cursor.getColumnIndex("employeeSalary");
            int employeeAgeIndex = cursor.getColumnIndex("employeeAge");
            do {
                long id = cursor.getLong(idColIndex);
                String employeeName = cursor.getString(employeeNameIndex);
                String employeeSalary = cursor.getString(employeeSalaryIndex);
                String employeeAge = cursor.getString(employeeAgeIndex);
                Employee employee = new Employee(id, employeeName, employeeSalary, employeeAge);
                employees.add(employee);
            } while (cursor.moveToNext());
            cursor.close();
            showEmployees(employees);
        } else {
            Toast.makeText(this, "Нет элементов в БД. Включите интернет.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 1) Проверять на наличие записей в БД
     * 2) Если есть, составить из них список Employee и отобразить
     * 3) Если нет показываем тост
     */


    // проверяем не пустая ли база данных
    // если нет считываем данных и показываем их

    /* showEmployees();*/
    //  ...
       /*  } else {
         Toast("Нет элементов в БД. Включите интернет.)
    }*/
    public void getDataFromServer() {
        appApiClient.getEmployees().enqueue(new Callback<EmployeesResponse>() {
            @Override
            public void onResponse(Call<EmployeesResponse> call, Response<EmployeesResponse> response) {
                EmployeesResponse employeesResponse = response.body();
                List<Employee> employeeList = employeesResponse.getEmployees();
                db.delete(DBHelper.TABLE_NAME, null, null);
                for (Employee employee : employeeList) {
                    String employeeName = employee.getEmployeeName();
                    String employeeSalary = employee.getEmployeeSalary();
                    String employeeAge = employee.getEmployeeAge();
                    long employeeId = employee.getId();
                    ContentValues cv = new ContentValues();
                    cv.put(EMPLOYEE_NAME, employeeName);
                    cv.put(EMPLOYEE_SALARY, employeeSalary);
                    cv.put(EMPLOYEE_AGE, employeeAge);
                    cv.put(EMPLOYEE_ID, employeeId);
                    db.insert(DBHelper.TABLE_NAME, null, cv);
                     /* 1) Удалять все записи
                      2) Потом добавляешь новые записи по одному
                    // db.insert(...)*/

                }
                showEmployees(employeeList);
            }

            @Override
            public void onFailure(Call<EmployeesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showEmployees(List<Employee> employees) {
        adapter.setEmployeeList(employees);
    }


    public void showConcreteEmployee(Employee employee) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(PARAM_EMPLOYEE_ID, employee.getId());
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}