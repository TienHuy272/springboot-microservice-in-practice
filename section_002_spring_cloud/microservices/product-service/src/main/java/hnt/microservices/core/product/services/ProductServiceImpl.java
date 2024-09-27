package hnt.microservices.core.product.services;

import static java.util.logging.Level.FINE;

import hnt.microservices.core.product.enties.ProductEntity;
import hnt.microservices.core.product.mapper.ProductMapper;
import hnt.microservices.core.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import hnt.api.core.product.Product;
import hnt.api.core.product.ProductService;
import hnt.api.exceptions.InvalidInputException;
import hnt.api.exceptions.NotFoundException;
import hnt.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ServiceUtil serviceUtil;

  private final ProductRepository repository;

  private final ProductMapper mapper;

  @Autowired
  public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {
    this.repository = repository;
    this.mapper = mapper;
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Mono<Product> createProduct(Product body) {

    if (body.getProductId() < 1) {
      throw new InvalidInputException("Invalid productId: " + body.getProductId());
    }

    ProductEntity entity = mapper.apiToEntity(body);
    Mono<Product> newEntity = repository.save(entity)
            .log(LOG.getName(), FINE)
            .onErrorMap(
                    DuplicateKeyException.class,
                    ex -> new InvalidInputException("Duplicate key, Product Id: " + body.getProductId()))
            .map(e -> mapper.entityToApi(e));

    return newEntity;
  }

  @Override
  public Mono<Product> getProduct(int productId) {

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    LOG.info("Will get product info for id={}", productId);

    return repository.findByProductId(productId)
            .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + productId)))
            .log(LOG.getName(), FINE)
            .map(e -> mapper.entityToApi(e))
            .map(e -> setServiceAddress(e));
  }

  @Override
  public Mono<Void> deleteProduct(int productId) {

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
    return repository.findByProductId(productId).log(LOG.getName(), FINE).map(e -> repository.delete(e)).flatMap(e -> e);
  }

  private Product setServiceAddress(Product e) {
    e.setServiceAddress(serviceUtil.getServiceAddress());
    return e;
  }
}
