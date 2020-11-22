package com.example.networkcalling.profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private ActivityProfileBinding binding;
    private ProfilePresenter presenter;

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

}
