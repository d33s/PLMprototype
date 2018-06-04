package com.enterprisesys.plm.customers;

import com.enterprisesys.plm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepo customersRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customersRepository.findAll().forEach(customers::add);
        return customers;
    }

    public Customer getCustomer(Integer id){
        return customersRepository.findById(id).orElse(null);
    }

    public void addCustomer(Customer customer){
        customersRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customersRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customersRepository.deleteById(id);
    }

    public void dropCustomersTable(){
        customersRepository.deleteAll();
    }

}
