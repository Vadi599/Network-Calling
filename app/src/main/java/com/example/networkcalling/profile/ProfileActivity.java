package com.example.networkcalling.profile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.databinding.ActivityProfileBinding;
import com.example.networkcalling.main.MainActivity;
import com.example.networkcalling.model.Employee;
import com.example.networkcalling.our_company.OurCompanyActivity;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private ActivityProfileBinding binding;
    private ProfileContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new ProfilePresenter(this, this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong(MainActivity.PARAM_EMPLOYEE_ID);
            if (isNetworkConnected()) {
                presenter.getEmployeeData(id);
            } else {
                presenter.getEmployeeDataFromDatabase(id);
            }
        }
        binding.btnAddToOurCompany.setOnClickListener(v -> presenter.addToCompanyEmployee());
    }

    @Override
    public void showEmployeeProfile(Employee employee) {
        binding.tvName.setText(employee.getEmployeeName());
        binding.tvSalary.setText(employee.getEmployeeSalary());
        binding.tvAge.setText(employee.getEmployeeAge());
        // visibility flags
        // VISIBLE - view видимо
        // INVISIBLE - view не видимо, но контент остается на макете
        // GONE - view не видимо, контент исчезает из макета

        binding.progressView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void showSuccessfulAddedToCompany() {
        Snackbar.make(binding.btnAddToOurCompany, "Успешно добавлен пользователь в НАШУ компанию", Snackbar.LENGTH_SHORT)
                .setAction("Список сотрудников", v -> {
                    showOurCompanyActivity();
                }).show();
    }

    public void showOurCompanyActivity() {
        Intent intent = new Intent(this, OurCompanyActivity.class);
        startActivity(intent);
    }

}
