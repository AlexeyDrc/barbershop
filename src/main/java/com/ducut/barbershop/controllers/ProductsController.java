package com.ducut.barbershop.controllers;


import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.Products;
import com.ducut.barbershop.repos.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/products")
    public String products(Model model) {

        Iterable<Products> products = productsRepository.findAll();
        model.addAttribute("products", products);

        return "products-main";
    }

    @GetMapping("/products/edit/{id}")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {

        Optional<Products> products = productsRepository.findById(id);
        ArrayList<Products> res = new ArrayList<>();
        products.ifPresent(res::add);
        model.addAttribute("products",res);
        model.addAttribute("productId",id);

        return "products-edit";
    }

    @PostMapping("/products/edit/{id}")
    public String productEdit(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam Number price, Model model)
    {
        Optional<Products> p = productsRepository.findById(id);
        Products product = p.get();
        product.setName(name);
        product.setPrice(price.doubleValue());
        productsRepository.save(product);
        return "redirect:/products";
    }
}
