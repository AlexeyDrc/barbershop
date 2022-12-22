package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Products, Long> {
}
