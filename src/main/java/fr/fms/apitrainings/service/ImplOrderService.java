package fr.fms.apitrainings.service;

import fr.fms.apitrainings.dao.CustomerRepository;
import fr.fms.apitrainings.dao.OrderItemRepository;
import fr.fms.apitrainings.dao.OrdersRepository;
import fr.fms.apitrainings.entities.Customer;
import fr.fms.apitrainings.entities.OrderItem;
import fr.fms.apitrainings.entities.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplOrderService implements IService<Orders> {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Optional<Orders> getOneById(long id) {
        return Optional.of(ordersRepository.getById(id));
    }

    @Override
    public Orders save(Orders obj) {
        Customer customer = obj.getCustomer();
        List<OrderItem> orderItems = obj.getOrderItems();

        ordersRepository.save(obj);
        long lastOrderId = getLastOrderId();

        for (OrderItem i : orderItems) {
            orderItemRepository.save(new OrderItem(null, i.getQuantity(), i.getPrice(), i.getTraining(), getOneById(lastOrderId).get()));
        }
        return getOneById(lastOrderId).get();
    }

    @Override
    public void delete(long id) {

    }

    public long getLastOrderId() {
        long lastInsertedId = 0;
        List<Orders> orders = ordersRepository.findAll();
        if (orders.size() != 0) {
            Orders lastOrder = orders.get(orders.size() - 1);
            lastInsertedId = lastOrder.getId();
        }
        return lastInsertedId;
    }
}
