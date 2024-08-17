package com.project.abc.controller.order;

import com.project.abc.dto.order.OrderDTO;
import com.project.abc.model.order.Order;
import com.project.abc.model.user.User;
import com.project.abc.service.order.OrderService;
import com.project.abc.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/order")
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        orderDTO.validate();
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(OrderDTO.init(order));
    }
}
