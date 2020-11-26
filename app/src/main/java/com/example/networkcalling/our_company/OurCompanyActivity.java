package com.example.networkcalling.our_company;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.adapter.EmployeesAdapter;
import com.example.networkcalling.databinding.ActivityOurCompanyBinding;
import com.example.networkcalling.model.Employee;

import java.util.List;

public class OurCompanyActivity extends AppCompatActivity implements OurCompanyContract.View {

    private ActivityOurCompanyBinding binding;
    private EmployeesAdapter adapter;
    private OurCompanyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOurCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new EmployeesAdapter();
        binding.rvEmployees.setAdapter(adapter);
        presenter = new OurCompanyPresenter(this, getBaseContext());
        presenter.getOurCompanyInfo();
    }

    @Override
    public void showEmployees(List<Employee> employees) {
        adapter.setEmployeeList(employees);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(OurCompanyActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}