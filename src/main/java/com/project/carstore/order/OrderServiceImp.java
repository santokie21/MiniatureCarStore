package com.project.carstore.order;

import com.project.carstore.customer.Address;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDTO;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.payment.PaymentDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service

public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private CustomerService customerService;






    @Override
    public Order createOrder(CustomerDTO customerDto, List<OrderItem> orderItems, PaymentDetails paymentDetails) throws OrderException {

        Order newOrder=null;
        if(customerDto.getId()==null)
        {
            throw new OrderException("customer doesn't exist");
        }
        newOrder=new Order(customerDto.getId(),customerDto.getFirstname(),customerDto.getLastname(),orderItems,paymentDetails);
        addOrderToCustomerOrdersList(newOrder);
        return this.orderRepository.save(newOrder);


    }

    @Override
    public List<Order> addOrderToCustomerOrdersList(Order newOrder) throws OrderException {
        if(newOrder.getId()==null)
        {
            throw new OrderException("order need to be created");
        }
        Optional<Customer> customerOptional=this.customerService.getCustomerById(newOrder.getCustomerID());
       if(customerOptional.isPresent())
       {
           customerOptional.get().getCustomerOrders().add(newOrder);
       }

      return customerOptional.get().getCustomerOrders();
    }

    @Override
    public Order getOrderById(Integer id) throws OrderException {
        if(id == 0)
        {
            throw new OrderException("Order should be exist");
        }
        return this.orderRepository.findById(id).get();
    }

    @Override
    public Order deleteOrderById(Integer id) {
       Optional<Order> orderOpt=this.orderRepository.findById(id);
       this.orderRepository.deleteById(id);
       return orderOpt.get();
    }

    @Override
    public Order updateOrder(Order newOrder) {
       return this.orderRepository.save(newOrder);

    }


    @Override
    public Integer getTotalPrices(List<OrderItem> orderItems) throws OrderException {
        if(orderItems.isEmpty())
        {
            throw  new OrderException("no item exist");
        }
        Integer totalPrice=0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
        }
        return totalPrice;
    }

    @Override
    public LocalDate getOrderDate() {
        return LocalDate.now();
    }

    @Override
    public Boolean paymentStatus(PaymentDetails paymentDetails) throws OrderException {
        if(paymentDetails.getOrderId()==null)
        {
            throw new OrderException("order is not exist to check the status");
        }
        return paymentDetails.getStatus();
    }

    @Override
    public String getOrderStatusById(Integer id) throws OrderException {
        if(this.getOrderById(id)==null)
        {
            throw new OrderException("order is not exist");
        }

         return this.getOrderById(id).getOrderStatus();
    }

    @Override
    public LocalDate getDeliveryDateByOrderId(Order order) throws OrderException {
        if(order==null)
        {
            throw new OrderException("order should be exist");
        }
        LocalDate orderDate=order.getOrderDate();
        return orderDate.plusDays(7);
    }

    @Override
    public Integer getTotalNumberOfItems(List<OrderItem> orderItems) throws OrderException {
        if(orderItems.isEmpty())
        {
            throw new OrderException("no item exists");
        }
        return orderItems.size();
    }

    @Override
    public Boolean checkAvailabilityOfOrderItem(Order order) {
        if(order.getId()==null)
        {
            return false;
        }
        return true;
    }


}
