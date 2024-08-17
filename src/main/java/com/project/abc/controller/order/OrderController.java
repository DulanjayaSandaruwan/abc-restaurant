package com.project.abc.controller.order;

import com.project.abc.dto.order.OrderDTO;
import com.project.abc.dto.order.UpdateOrderStatusDTO;
import com.project.abc.model.order.Order;
import com.project.abc.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update-order-status/{id}")
    public ResponseEntity<OrderDTO> updateItem(
            @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO,
            @PathVariable String id
    ) {
        updateOrderStatusDTO.validate();
        Order order = orderService.updateOrderStatus(updateOrderStatusDTO, id);
        OrderDTO orderDTO = OrderDTO.init(order);
        return ResponseEntity.ok(orderDTO);
    }
}
