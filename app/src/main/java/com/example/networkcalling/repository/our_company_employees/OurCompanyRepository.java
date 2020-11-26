package com.example.networkcalling.repository.our_company_employees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.networkcalling.model.Employee;
import com.example.networkcalling.repository.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class OurCompanyRepository implements IOurCompanyRepository {

    private final String EMPLOYEE_NAME = "employeeName";
    private final String EMPLOYEE_SALARY = "employeeSalary";
    private final String EMPLOYEE_AGE = "employeeAge";
    private final String EMPLOYEE_ID = "id";

    private DBHelper dbHelper;
    SQLiteDatabase db;

    public OurCompanyRepository(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public List<Employee> getEmployees() {
        Cursor cursor = db.query(DBHelper.TABLE_NAME_OUR_COMPANY_WORKERS, null, null, null, null, null, null);
        boolean isExistDatabaseRecords = cursor.moveToFirst();
        ArrayList<Employee> employees = new ArrayList<>();
        if (isExistDatabaseRecords) {
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
            return employees;
        } else {
            return null;
        }
    }

    @Override
    public void deleteAllRows() {
        db.delete(DBHelper.TABLE_NAME_OUR_COMPANY_WORKERS, null, null);
    }

    @Override
    public void insertEmployee(Employee employee) {
        String employeeName = employee.getEmployeeName();
        String employeeSalary = employee.getEmployeeSalary();
        String employeeAge = employee.getEmployeeAge();
        long employeeId = employee.getId();
        ContentValues cv = new ContentValues();
        cv.put(EMPLOYEE_NAME, employeeName);
        cv.put(EMPLOYEE_SALARY, employeeSalary);
        cv.put(EMPLOYEE_AGE, employeeAge);
        cv.put(EMPLOYEE_ID, employeeId);
        db.insert(DBHelper.TABLE_NAME_OUR_COMPANY_WORKERS, null, cv);
    }

    @Override
    public Employee getEmployee(long id) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from " + DBHelper.TABLE_NAME_OUR_COMPANY_WORKERS + " where id = ?", new String[]{String.valueOf(id)});
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
            return employee;
        } else {
            return null;
        }
    }

}
