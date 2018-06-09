package com.enterprisesys.plm.controller;

import com.enterprisesys.plm.model.Employee;
import com.enterprisesys.plm.model.Order;
import com.enterprisesys.plm.service.EmployeesService;
import com.enterprisesys.plm.service.OrdersService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EmployeesService employeesService;

    private static class OrderWithEmployeesAsString{
        @Getter @Setter private Integer id;
        @Getter @Setter private String customerID;
        @Getter @Setter private String assemblyID;
        @Getter @Setter private String orderDate;
        @Getter @Setter private String peopleResponsible;
        @Getter @Setter private String status;

        OrderWithEmployeesAsString(){}

        OrderWithEmployeesAsString(Order regular){
            this.id = regular.getIdOrder();
            this.customerID = regular.getCustomerID();
            this.assemblyID = regular.getAssemblyID();
            this.orderDate = regular.getOrderDate();

            //result is not ordered because we use hashSet to contain Employees
            StringBuilder employees = new StringBuilder();
            for (Employee e : regular.getEmployees()){
                employees.append(e.getIdEmployee().toString());
                employees.append(", ");
            }
            String finalList = employees.toString();
            if(finalList.length() >= 2) finalList = finalList.substring(0, finalList.length() - 2);
            this.setPeopleResponsible(finalList);

            this.status = regular.getStatus();
        }

        private Order translateToRegularOrder() {
            Order toReturn = new Order();
            toReturn.setIdOrder(this.getId());
            toReturn.setCustomerID(this.getCustomerID());
            toReturn.setAssemblyID(this.getAssemblyID());
            toReturn.setOrderDate(this.getOrderDate());
            toReturn.setEmployees(new HashSet<>());
            toReturn.setStatus(this.getStatus());
            return toReturn;
        }
    }

    @RequestMapping("/orders/all")
    public List<OrderWithEmployeesAsString> getAllOrders() {

        ArrayList<Order> orders = new ArrayList<>();
        orders.addAll(ordersService.getAllOrders());

        ArrayList<OrderWithEmployeesAsString> toReturn = new ArrayList<>();
        for (Order o : orders) {
            OrderWithEmployeesAsString orderWithString = new OrderWithEmployeesAsString(o);
            toReturn.add(orderWithString);
        }
        return toReturn;
    }

    @RequestMapping("/orders/{id}")
    public OrderWithEmployeesAsString getOneOrder(@PathVariable Integer id){
        return new OrderWithEmployeesAsString(ordersService.getOrder(id));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public void addOrder(@RequestBody OrderWithEmployeesAsString orderFromForm){
        Order order = new Order(orderFromForm.translateToRegularOrder());
        String properIDs = orderFromForm.getPeopleResponsible().replaceAll("\\s+", "");
        properIDs = properIDs.replaceAll("[^0-9]", " ");
        String[] IDs = properIDs.split(" ");
        for (String s : IDs) {
            order.addEmployee(employeesService.getEmployee(Integer.parseInt(s)));
        }
        ordersService.addOrder(order);
    }

    @PostMapping("/orders/{idOrder}/employees/{idWorker}")
    public void addWorkerToOrder(@PathVariable Integer idOrder, @PathVariable Integer idWorker) {
        Order updated = new Order(ordersService.getOrder(idOrder));
        updated.addEmployee(employeesService.getEmployee(idWorker));
        ordersService.updateOrder(updated);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
    public void updateOrder(@RequestBody OrderWithEmployeesAsString orderFromForm, @PathVariable Integer id){
        orderFromForm.setId(id);
        Order updated = new Order(orderFromForm.translateToRegularOrder());
        String properIDs = orderFromForm.getPeopleResponsible().replaceAll("\\s+", "");
        properIDs = properIDs.replaceAll("[^0-9]", " ");
        String[] IDs = properIDs.split(" ");
        for (String s : IDs) {
            updated.addEmployee(employeesService.getEmployee(Integer.parseInt(s)));
        }
        ordersService.updateOrder(updated);
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