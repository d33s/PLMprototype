package com.enterprisesys.plm.repository;

        import com.enterprisesys.plm.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

}
