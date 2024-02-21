package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.payment.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController

public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("createOrder")
    public Order createOrder(@RequestBody CustomerDTO customerDto, @RequestBody List<OrderItem> orderItems, @RequestBody  PaymentDetails paymentDetails)
    {
        return this.orderService.createOrder(customerDto,orderItems,paymentDetails);
  }

    @GetMapping("getOrder")
    public Order getOrderById(@RequestBody Integer id) throws OrderException {
        return this.orderService.getOrderById(id);
    }
    @DeleteMapping("deleteOrder/{id}")
    public Order deleteOrderById(@PathVariable("id") Integer id)
    {
        return this.orderService.deleteOrderById(id);
    }
    @PatchMapping("updateOrder")
    public Order updateOrder(@RequestBody Order newOrder)
    {
        return  this.orderService.updateOrder(newOrder);
    }

    @GetMapping("orderDate")
    public LocalDate getOrderDate()
    {
        return this.orderService.getOrderDate();
    }
    @GetMapping("totalPrice")
    public Integer getTotalPrice(@RequestBody List<OrderItem> orderItems)
    {
        return  this.orderService.getTotalPrices(orderItems);
    }
    @GetMapping("paymentStatus")
    public Boolean paymentStatus(@RequestBody PaymentDetails paymentDetails)
    {
        return this.orderService.paymentStatus(paymentDetails);
    }
    @GetMapping("orderStatus/{id}")
    public String getOrderStatus(@PathVariable("id") Integer id) throws OrderException {
        return this.orderService.getOrderStatusById(id);
    }

}
