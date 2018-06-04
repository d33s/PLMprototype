package com.enterprisesys.plm.customers;

import com.enterprisesys.plm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @RequestMapping("/customers/all")
    public List<Customer> getAllCustomers() {
        return customersService.getAllCustomers();
    }

    @RequestMapping("/customers/{id}")
    public Customer getOneCustomer(@PathVariable Integer id){
        return customersService.getCustomer(id);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public void addCustomer(@RequestBody Customer customer){
        customersService.addCustomer(customer);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable Integer id){
        customer.setIdCustomer(id);
        customersService.updateCustomer(customer);
    }

    @RequestMapping(value = "/customers/del/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Integer id){
        customersService.deleteCustomer(id);
    }

    @RequestMapping(value = "/customers/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        customersService.dropCustomersTable();
    }

}