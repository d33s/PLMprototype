package com.enterprisesys.plm.employees;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface EmployeesRepo extends CrudRepository<Employee, Integer> {

}
