package hnt.microservices.core.recommendation.repository;

import hnt.microservices.core.recommendation.entities.Recommendation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, String> {
    List<Recommendation> findByProductId(int productId);
}
