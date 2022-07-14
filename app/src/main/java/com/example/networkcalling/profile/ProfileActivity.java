package com.example.networkcalling.profile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.R;
import com.example.networkcalling.databinding.ActivityProfileBinding;
import com.example.networkcalling.databinding.CustomDialogDeleteBinding;
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
        binding.btnDeleteToOurCompany.setOnClickListener(v -> showDialogDeleteUser());
    }

    public void showDialogDeleteUser() {
        /* Систменый диалог
        new AlertDialog.Builder(this)
                .setTitle(R.string.attention)
                .setMessage(R.string.are_you_sure_you_want_delete_user_from_our_company)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    presenter.deleteFromCompanyEmployee();
                    dialog.dismiss();
                }).show();
         */

        // кастомный диалог
        CustomDialogDeleteBinding customDialogDeleteBinding = CustomDialogDeleteBinding.inflate(getLayoutInflater());
        customDialogDeleteBinding.tvTitle.setText(R.string.attention);
        customDialogDeleteBinding.tvDescription.setText(R.string.are_you_sure_you_want_delete_user_from_our_company);

        AlertDialog customAlertBuilder = new AlertDialog.Builder(this)
                .setView(customDialogDeleteBinding.getRoot())
                .create();

        customDialogDeleteBinding.btnCancel.setOnClickListener(v -> {
            customAlertBuilder.dismiss();
        });
        customDialogDeleteBinding.btnOk.setOnClickListener(v -> {
            presenter.deleteFromCompanyEmployee();
            customAlertBuilder.dismiss();
        });

        customAlertBuilder.show();
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
        presenter.checkUserExistInOurCompany(employee);

        binding.progressView.setVisibility(View.GONE);
    }

    @Override
    public void showButtonsState(boolean isExistUserInOurCompany) {
        if (isExistUserInOurCompany) {
            binding.btnAddToOurCompany.setVisibility(View.GONE);
            binding.btnDeleteToOurCompany.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddToOurCompany.setVisibility(View.VISIBLE);
            binding.btnDeleteToOurCompany.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notShowLoading() {
        binding.progressView.setVisibility(View.GONE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void showSuccessfulAddedToCompany() {
        Snackbar.make(binding.btnAddToOurCompany, "Успешно добавлен пользователь в компанию", Snackbar.LENGTH_SHORT)
                .setAction("Список сотрудников", v -> {
                    showOurCompanyActivity();
                }).show();
    }

    @Override
    public void showSuccessfulDeletedFromCompany() {
        Snackbar.make(binding.btnAddToOurCompany, "Успешно удалён пользователь из компанию", Snackbar.LENGTH_SHORT)
                .setAction("Список сотрудников", v -> {
                    showOurCompanyActivity();
                }).show();
    }

    public void showOurCompanyActivity() {
        Intent intent = new Intent(this, OurCompanyActivity.class);
        startActivity(intent);
    }

}
