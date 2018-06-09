package com.enterprisesys.plm.controller;

import com.enterprisesys.plm.model.Employee;
import com.enterprisesys.plm.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping("/employees/all")
    public List<Employee> getAllEmployees() {
        return employeesService.getAllEmployees();
    }

    @RequestMapping("/employees/{id}")
    public Employee getOneEmployee(@PathVariable Integer id){
        return employeesService.getEmployee(id);
    }

    @RequestMapping("/employees/order/{orderId}")
    public List<Employee> getAllEmployeesParticipatingInOneOrder(@PathVariable Integer orderId){
        return employeesService.getAllEmployeesParticipatingInOneOrder(orderId);
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public void addEmployee(@RequestBody Employee employee){
        employeesService.addEmployee(employee);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    public void updateEmployee(@RequestBody Employee employee, @PathVariable Integer id){
        employee.setIdEmployee(id);
        employeesService.updateEmployee(employee);
    }

    @RequestMapping(value = "/employees/del/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable Integer id){
        employeesService.deleteEmployee(id);
    }

    @RequestMapping(value = "/employees/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        employeesService.dropEmployeesTable();
    }

}