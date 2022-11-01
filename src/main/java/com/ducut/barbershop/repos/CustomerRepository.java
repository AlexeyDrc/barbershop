package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
