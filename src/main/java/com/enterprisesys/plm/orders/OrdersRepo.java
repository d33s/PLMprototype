package com.enterprisesys.plm.orders;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrdersRepo extends CrudRepository<Order, Integer> {

}
