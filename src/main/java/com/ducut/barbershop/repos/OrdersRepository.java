package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders o ORDER BY o.date ASC",
            nativeQuery = true)
    List<Orders> findByDateASC();

    @Query(value = "SELECT * FROM orders o ORDER BY o.date ASC",
            nativeQuery = true)
    List<Orders> findByDateDECS();
}
