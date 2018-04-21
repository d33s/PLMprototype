package com.enterprisesys.plm.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/orders/all")
    public List<Order> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @RequestMapping("/orders/{id}")
    public Order getOneOrder(@PathVariable Integer id){
        return ordersService.getOrder(id);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public void addOrder(@RequestBody Order order){
        ordersService.addOrder(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
    public void updateOrder(@RequestBody Order order, @PathVariable Integer id){
        order.setIdOrder(id);
        ordersService.updateOrder(order);
    }

    @RequestMapping(value = "/orders/del/{id}", method = RequestMethod.DELETE)
    public void deletePart(@PathVariable Integer id){
        ordersService.deleteOrder(id);
    }

    @RequestMapping(value = "/orders/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        ordersService.dropOrdersTable();
    }

}