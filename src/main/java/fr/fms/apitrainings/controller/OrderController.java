package fr.fms.apitrainings.controller;

import fr.fms.apitrainings.entities.Orders;
import fr.fms.apitrainings.service.ImplOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class OrderController {

    private int orderNumber = 20220;

    @Autowired
    private ImplOrderService implOrderService;

    @PostMapping("/order")
    public Orders saveOrder(@RequestBody Orders orders) {
        orders.setNumber(orderNumber += 1);
        return implOrderService.save(orders);
    }

}
