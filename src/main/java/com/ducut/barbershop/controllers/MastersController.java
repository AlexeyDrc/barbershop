package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.Test.rateCalculating;
import com.ducut.barbershop.models.MastersReviews;
import com.ducut.barbershop.repos.MastersRepository;
import com.ducut.barbershop.repos.MastersReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MastersController {

    @Autowired
    private MastersRepository mastersRepository;
    @Autowired
    private MastersReviewsRepository mastersReviewsRepository;

    @GetMapping("/masters")
    public String masters(Model model) {
    /*    int rate = rateCalculating.getRate();
        model.addAttribute("rate", rate);*/
        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        Iterable<MastersReviews> mastersReviews = mastersReviewsRepository.findAll();
        model.addAttribute("masRev", mastersReviews);

        return "masters-main";
    }


  @GetMapping("/masters/{id}")
  public String masterDetail(@PathVariable(value = "id") long id, Model model) {

      Optional<Masters> master = mastersRepository.findById(id);
      ArrayList<Masters> res = new ArrayList<>();
      master.ifPresent(res::add);
      model.addAttribute("master", res);

      Iterable<MastersReviews> mastersReviews = mastersReviewsRepository.findAll();
      model.addAttribute("masRev", mastersReviews);

      return "masters-reviews";
  }

}