package com.enterprisesys.plm.repository;

import com.enterprisesys.plm.model.Order;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrdersRepo extends CrudRepository<Order, Integer> {

}
