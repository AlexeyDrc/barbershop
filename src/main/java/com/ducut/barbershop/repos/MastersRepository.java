package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.MastersReviews;
import com.ducut.barbershop.models.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MastersRepository extends CrudRepository<Masters, Long> {



}
