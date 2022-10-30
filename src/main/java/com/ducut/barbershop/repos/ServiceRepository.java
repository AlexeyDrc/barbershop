package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.MastersReviews;
import com.ducut.barbershop.models.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends CrudRepository<Service, Long> {

}
