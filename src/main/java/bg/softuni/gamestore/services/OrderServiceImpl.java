package bg.softuni.gamestore.services;

import bg.softuni.gamestore.entities.Order;
import bg.softuni.gamestore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void removeProductFromOrder(Order order) {
        this.orderRepository.saveAndFlush(order);
    }
}
