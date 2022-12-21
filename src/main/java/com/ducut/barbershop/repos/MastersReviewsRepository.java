package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.MastersReviews;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MastersReviewsRepository extends CrudRepository<MastersReviews, Long> {

    @Query(value = "SELECT * FROM `masters_reviews` ORDER BY `masters_reviews`.`rate` ASC",
            nativeQuery = true)
    List<MastersReviews> findByRateASC();


    @Query(value = "SELECT * FROM `masters_reviews` ORDER BY `masters_reviews`.`date` DESC",
            nativeQuery = true)
    List<MastersReviews> findByDateDESC();

    @Query(value = "SELECT * FROM `masters_reviews` ORDER BY `masters_reviews`.`master_id` ASC",
            nativeQuery = true)
    List<MastersReviews> findByMasterASC();
}
