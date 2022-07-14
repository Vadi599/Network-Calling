package com.example.networkcalling.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.R;
import com.example.networkcalling.adapter.EmployeesAdapter;
import com.example.networkcalling.databinding.ActivityMainBinding;
import com.example.networkcalling.model.Employee;
import com.example.networkcalling.our_company.OurCompanyActivity;
import com.example.networkcalling.profile.ProfileActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    public static final String PARAM_EMPLOYEE_ID = "MainActivity.EMPLOYEE_ID";
    private ActivityMainBinding binding;
    private EmployeesAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new EmployeesAdapter();
        binding.rvEmployees.setAdapter(adapter);
        presenter = new MainPresenter(this, getBaseContext());
        adapter.setOnItemClickListener(this::showConcreteEmployee);
        if (isNetworkConnected()) {
            presenter.getDataFromServer();
        } else {
            Toast.makeText(this, "Нет инета тянем из БД", Toast.LENGTH_SHORT).show();
            presenter.getDataFromDatabase();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_create_employee).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_company:
                Intent intent = new Intent(this, OurCompanyActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showEmployees(List<Employee> employees) {
        adapter.setEmployeeList(employees);
        notShowLoading();
    }

    @Override
    public void notShowLoading() {
        binding.progressView.setVisibility(View.GONE);
    }

    @Override
    public void showConcreteEmployee(Employee employee) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(PARAM_EMPLOYEE_ID, employee.getId());
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}