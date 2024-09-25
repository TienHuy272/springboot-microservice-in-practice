package hnt.microservices.core.product.repository;

import hnt.microservices.core.product.enties.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String>, CrudRepository<Product, String> {
    Optional<Product> findByProductId(int productId);
}
