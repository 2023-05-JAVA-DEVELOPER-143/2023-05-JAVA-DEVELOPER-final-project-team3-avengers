package com.danaga.controller;

import com.danaga.dto.OrderGuestDto;
import com.danaga.dto.OrdersProductDto;
import com.danaga.service.*;

import lombok.*;

import com.danaga.dto.OrdersDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/guestProductOrder")
    public String guestProductOrder(OrdersProductDto ordersProductDto, OrderGuestDto orderGuestDto, Model model) {
        try {
            OrdersDto ordersDto = orderService.guestProductOrderSave(ordersProductDto, orderGuestDto);
            model.addAttribute("ordersDto", ordersDto);
            return "orders/orders";
        } catch (Exception e) {
            e.printStackTrace(); 
            model.addAttribute("errorMsg", e.getMessage());
            return "error/error_page";
        }
    }
}
