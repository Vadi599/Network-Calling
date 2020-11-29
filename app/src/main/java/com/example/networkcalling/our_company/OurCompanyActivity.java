package com.example.networkcalling.our_company;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.networkcalling.R;
import com.example.networkcalling.adapter.EmployeesAdapter;
import com.example.networkcalling.adapter.EmployeesWithDeleteAdapter;
import com.example.networkcalling.adapter.OnDeleteItemClickListener;
import com.example.networkcalling.adapter.OnItemClickListener;
import com.example.networkcalling.databinding.ActivityOurCompanyBinding;
import com.example.networkcalling.databinding.CustomDialogAddEmployeeBinding;
import com.example.networkcalling.databinding.CustomDialogDeleteBinding;
import com.example.networkcalling.databinding.CustomDialogDeleteFromOurCompanyBinding;
import com.example.networkcalling.databinding.CustomDialogEditEmployeeBinding;
import com.example.networkcalling.databinding.ListItemEmployeeWithDeleteBinding;
import com.example.networkcalling.model.Employee;
import java.util.List;

public class OurCompanyActivity extends AppCompatActivity implements OurCompanyContract.View {

    private ActivityOurCompanyBinding binding;
    private EmployeesWithDeleteAdapter adapter;
    private OurCompanyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOurCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new EmployeesWithDeleteAdapter();
        binding.rvEmployees.setAdapter(adapter);
        presenter = new OurCompanyPresenter(this, getBaseContext());
        presenter.getOurCompanyInfo();
        adapter.setOnDeleteItemClickListener(new OnDeleteItemClickListener() {
            @Override
            public void onItemClicked(Employee employee) {
                editEmployee(employee);
            }

            @Override
            public void onDeleteItemClicked(Employee employee) {
                deleteDialog(employee);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_employee:
                addEmployee();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addEmployee() {
        CustomDialogAddEmployeeBinding customDialogAddEmployeeBinding = CustomDialogAddEmployeeBinding.inflate(getLayoutInflater());
        customDialogAddEmployeeBinding.tvTitle.setText(R.string.add_employee);

        customDialogAddEmployeeBinding.tvEmployeeName.setText(R.string.employee_add_name);

        customDialogAddEmployeeBinding.tvEmployeeSalary.setText(R.string.employee_add_salary);

        customDialogAddEmployeeBinding.tvEmployeeAge.setText(R.string.employee_add_age);

        AlertDialog customAlertBuilder = new AlertDialog.Builder(this)
                .setView(customDialogAddEmployeeBinding.getRoot())
                .create();

        customDialogAddEmployeeBinding.btnAddCancel.setOnClickListener(v -> {
            customAlertBuilder.dismiss();
        });
        customDialogAddEmployeeBinding.btnAddOk.setOnClickListener(v -> {

            String name = customDialogAddEmployeeBinding.etName.getText().toString();
            String age = customDialogAddEmployeeBinding.etAge.getText().toString();
            String salary = customDialogAddEmployeeBinding.etSalary.getText().toString();

            Employee employee = new Employee(name, salary, age);

            presenter.addEmployee(employee);
            customAlertBuilder.dismiss();
        });
        customAlertBuilder.show();
    }


    /**
     * 1) Добавить возможность добавления штатного сотрудника через тулбар иконка "+"
     * 2) При нажатии на плюсик открывается диалог (как при редактировании, можешь скопипастить, толькь смысл - Добавление)
     */

    /**
     * 1) Добавить возможность удаления штатного сотрудника
     * 2) Удаление должно происходить при помощи диалога (как в детальном профиле)
     * 3) После удаления конкретного юзера обновлять список ( как после редактирования)
     */
    public void deleteDialog(Employee employee) {
        CustomDialogDeleteFromOurCompanyBinding customDialogDeleteFromOurCompanyBinding = CustomDialogDeleteFromOurCompanyBinding.inflate(getLayoutInflater());
        customDialogDeleteFromOurCompanyBinding.tvEmployeeTitle.setText(R.string.delete_employee_from_our_company);
        customDialogDeleteFromOurCompanyBinding.tvEmployeeDescription.setText(R.string.delete_employee_from_this_company);
        AlertDialog customAlertBuilder = new AlertDialog.Builder(this)
                .setView(customDialogDeleteFromOurCompanyBinding.getRoot())
                .create();
        customDialogDeleteFromOurCompanyBinding.btnDeleteCancel.setOnClickListener(v -> {
            customAlertBuilder.dismiss();
        });
        customDialogDeleteFromOurCompanyBinding.btnDeleteOk.setOnClickListener(v -> {
            presenter.deleteFromOurCompanyEmployee(employee.getId());
            customAlertBuilder.dismiss();
        });
        customAlertBuilder.show();
    }

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