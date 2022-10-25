package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {


}
