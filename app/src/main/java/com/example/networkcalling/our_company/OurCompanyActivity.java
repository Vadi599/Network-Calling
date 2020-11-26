package com.example.networkcalling.our_company;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.networkcalling.R;
import com.example.networkcalling.adapter.EmployeesAdapter;
import com.example.networkcalling.adapter.OnItemClickListener;
import com.example.networkcalling.databinding.ActivityOurCompanyBinding;
import com.example.networkcalling.databinding.CustomDialogEditEmployeeBinding;
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
        adapter.setOnItemClickListener(this::editEmployee);
    }

    /**
     * 1) Добавить возможность удаления штатного сотрудника
     * 2) Удаление должно происходить при помощи диалога (как в детальном профиле)
     * 3) После удаления конкретного юзера обновлять список ( как после редактирования)
     *
     */

    /**
     * 1) Добавить возможность добавления штатного сотрудника через тулбар иконка "+"
     * 2) При нажатии на плюсик открывается диалог (как при редактировании, можешь скопипастить, толькь смысл - Добавление)
     *
     */


    public void editEmployee(Employee employee) {
        CustomDialogEditEmployeeBinding customDialogEditEmployeeBinding = CustomDialogEditEmployeeBinding.inflate(getLayoutInflater());
        customDialogEditEmployeeBinding.tvTitle.setText(R.string.edit_employee);

        customDialogEditEmployeeBinding.tvEmployeeName.setText(R.string.employee_name);
        customDialogEditEmployeeBinding.etName.setText(employee.getEmployeeName());

        customDialogEditEmployeeBinding.tvEmployeeSalary.setText(R.string.employee_salary);
        customDialogEditEmployeeBinding.etSalary.setText(employee.getEmployeeSalary());

        customDialogEditEmployeeBinding.tvEmployeeAge.setText(R.string.employee_age);
        customDialogEditEmployeeBinding.etAge.setText(employee.getEmployeeAge());

        AlertDialog customAlertBuilder = new AlertDialog.Builder(this)
                .setView(customDialogEditEmployeeBinding.getRoot())
                .create();

        customDialogEditEmployeeBinding.btnEditCancel.setOnClickListener(v -> {
            customAlertBuilder.dismiss();
        });
        customDialogEditEmployeeBinding.btnEditOk.setOnClickListener(v -> {

            String parsedName = customDialogEditEmployeeBinding.etName.getText().toString();
            String parsedSalary = customDialogEditEmployeeBinding.etSalary.getText().toString();
            String parsedAge = customDialogEditEmployeeBinding.etAge.getText().toString();

            employee.setEmployeeAge(parsedAge);
            employee.setEmployeeSalary(parsedSalary);
            employee.setEmployeeName(parsedName);
            presenter.editEmployee(employee);
            customAlertBuilder.dismiss();
        });

        customAlertBuilder.show();
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