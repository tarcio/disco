package disco.sales.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import disco.sales.entity.Sale;

@Repository
public interface SaleRepository extends MongoRepository<Sale, String> {

	public Page<Sale> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
