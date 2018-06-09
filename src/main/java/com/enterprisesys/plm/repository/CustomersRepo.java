package com.enterprisesys.plm.repository;

        import com.enterprisesys.plm.model.Customer;
        import org.springframework.data.repository.CrudRepository;

        import javax.transaction.Transactional;

@Transactional
public interface CustomersRepo extends CrudRepository<Customer, Integer> {

}
