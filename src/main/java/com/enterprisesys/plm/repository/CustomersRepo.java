package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("customersRepo")
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

}
