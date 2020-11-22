package com.example.networkcalling.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkcalling.databinding.ListItemEmployeeBinding;
import com.example.networkcalling.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList = new ArrayList<>();

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new EmployeeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employee, parent, false));
        ListItemEmployeeBinding binding = ListItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EmployeeViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = getEmployeeList().get(position);
        holder.tvAge.setText(employee.getEmployeeAge());
        holder.tvSalary.setText(employee.getEmployeeSalary());
        holder.tvName.setText(employee.getEmployeeName());
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClicked(employee);
            }
        });
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvSalary;
        public TextView tvAge;

        public EmployeeViewHolder(ListItemEmployeeBinding binding) {
            super(binding.getRoot());
            /*tvName = itemView.findViewById(R.id.tvName);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvAge = itemView.findViewById(R.id.tvAge);*/
            tvName = binding.tvName;
            tvSalary = binding.tvSalary;
            tvAge = binding.tvAge;

        }
    }

}
