package com.project.abc.service.order;

import com.project.abc.commons.exceptions.http.InsufficientStockException;
import com.project.abc.dto.order.OrderDTO;
import com.project.abc.dto.order.OrderDetailDTO;
import com.project.abc.model.item.Item;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.OrderDetail;
import com.project.abc.repository.order.OrderDetailRepository;
import com.project.abc.repository.order.OrderRepository;
import com.project.abc.security.Session;
import com.project.abc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ItemService itemService;

    public Order createOrder(OrderDTO orderDTO) {
        log.info("place order by user {}", Session.getUser().getId());
        Order order = Order.init(orderDTO, Session.getUser());

        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
            Item item = itemService.getItemById(detailDTO.getItem().getId());

            if (item.getQtyOnHand() < detailDTO.getQuantity()) {
                throw new InsufficientStockException("Not enough quantity available for item " + item.getId());
            }

            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getQuantity());
            itemService.reduceQtyOnHand(item);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(UUID.randomUUID().toString());
            orderDetail.setQuantity(detailDTO.getQuantity());
            orderDetail.setItem(item);
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);;
        }
        order.setOrderDetails(orderDetails);

//        if (orderDTO.getPayment() != null) {
//            PaymentDTO paymentDTO = orderDTO.getPayment();
//            Payment payment = new Payment();
//            payment.setId(UUID.randomUUID().toString());
//            payment.setPaymentDate(paymentDTO.getPaymentDate());
//            payment.setAmount(paymentDTO.getAmount());
//            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
//            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
//            payment.setOrder(order);
//            order.setPayment(payment);
//        }

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.saveAll(order.getOrderDetails());

//        if (order.getPayment() != null) {
//            paymentRepository.save(order.getPayment());
//        }

        return savedOrder;
    }
}
