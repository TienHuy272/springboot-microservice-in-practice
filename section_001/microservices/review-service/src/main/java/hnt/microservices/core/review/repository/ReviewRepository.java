package hnt.microservices.core.review.repository;

import hnt.microservices.core.review.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Transactional(readOnly = true)
    List<Review> findByProductId(int productId);
}
