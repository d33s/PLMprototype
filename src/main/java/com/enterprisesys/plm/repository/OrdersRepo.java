package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Assembly;
import com.enterprisesys.plm.model.Employee;
import com.enterprisesys.plm.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrdersRepo extends CrudRepository<Order, Integer> {

}
