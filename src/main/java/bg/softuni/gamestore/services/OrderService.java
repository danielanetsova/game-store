package bg.softuni.gamestore.services;

import bg.softuni.gamestore.entities.Order;

public interface OrderService {
    void saveOrder(Order order);
    void removeProductFromOrder(Order order);
}
