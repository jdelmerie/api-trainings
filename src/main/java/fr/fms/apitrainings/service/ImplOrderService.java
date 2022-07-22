package fr.fms.apitrainings.service;

import fr.fms.apitrainings.dao.CustomerRepository;
import fr.fms.apitrainings.dao.OrdersRepository;
import fr.fms.apitrainings.entities.Customer;
import fr.fms.apitrainings.entities.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplOrderService implements IService<Orders>  {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Optional<Orders> getOneById(long id) {
        return Optional.empty();
    }

    @Override
    public Orders save(Orders obj) {
        return ordersRepository.save(obj);
    }

    @Override
    public void delete(long id) {

    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
