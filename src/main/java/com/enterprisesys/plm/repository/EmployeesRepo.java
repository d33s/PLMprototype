package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Employee;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface EmployeesRepo extends CrudRepository<Employee, Integer> {

}
