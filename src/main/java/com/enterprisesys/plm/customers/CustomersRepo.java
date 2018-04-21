package com.enterprisesys.plm.customers;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

}
