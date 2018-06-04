package com.enterprisesys.plm.customers;

import com.enterprisesys.plm.model.Customer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

}
