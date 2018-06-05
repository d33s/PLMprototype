package com.enterprisesys.plm.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepo employeesRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeesRepository.findAll().forEach(employees::add);
        return employees;
    }

    public Employee getEmployee(Integer id){
        return employeesRepository.findById(id).orElse(null);
    }

    public void addEmployee(Employee employee){
        employeesRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeesRepository.save(employee);
    }

    public void deleteEmployee(Integer id) {
        employeesRepository.deleteById(id);
    }

    public void dropEmployeesTable(){
        employeesRepository.deleteAll();
    }

}
