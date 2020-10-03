package com.practice.mealoptimizer.web;

import com.practice.mealoptimizer.domain.Order;
import com.practice.mealoptimizer.dto.request.OrderRequestDTO;
import com.practice.mealoptimizer.dto.response.OrderResponseDTO;
import com.practice.mealoptimizer.mapper.OrderMapper;
import com.practice.mealoptimizer.mapper.ResultMapper;
import com.practice.mealoptimizer.processor.OptimizationType;
import com.practice.mealoptimizer.processor.Optimizer;
import com.practice.mealoptimizer.processor.OptimizerFactory;
import com.practice.mealoptimizer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/mealoptimizer/orders")
public class OrderController {

    private OrderMapper orderMapper;

    private ResultMapper resultMapper;

    private OptimizerFactory optimizerFactory;

    private OrderService orderService;

    @Autowired
    public OrderController(OrderMapper orderMapper, ResultMapper resultMapper, OptimizerFactory optimizerFactory, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.resultMapper = resultMapper;
        this.optimizerFactory = optimizerFactory;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", path = "/save")
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        try {

            Order order = orderMapper.orderDTOtoOrder(orderRequestDTO);
            Optimizer optimizer = optimizerFactory.getOptimizerByType(OptimizationType.COST);
            Map<String, Object> result = optimizer.optimizeByOptimizationType(order);
            order = resultMapper.mapResultToOrder(result, order);
            OrderResponseDTO responseDTO = orderMapper.ordertoOrderDTO(orderService.saveOrder(order));
            return new ResponseEntity<OrderResponseDTO>(responseDTO, HttpStatus.CREATED);

        } catch (RuntimeException re) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, re.getMessage());
        }
    }
}