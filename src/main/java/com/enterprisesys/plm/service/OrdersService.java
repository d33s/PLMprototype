package com.enterprisesys.plm.service;

import com.enterprisesys.plm.model.Employee;
import com.enterprisesys.plm.model.Order;
import com.enterprisesys.plm.repository.EmployeesRepo;
import com.enterprisesys.plm.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepo ordersRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        ordersRepository.findAll().forEach(orders::add);
        return orders;
    }

    public Order getOrder(Integer id){
        return ordersRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order){
        ordersRepository.save(order);
    }

    public void updateOrder(Order order) {
        ordersRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        ordersRepository.deleteById(id);
    }

    public void dropOrdersTable(){
        ordersRepository.deleteAll();
    }

}
