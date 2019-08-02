package disco.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import disco.catalog.entity.Disco;
import disco.commons.constant.Genre;

@Repository
public interface DiscoRepository extends MongoRepository<Disco, String> {

	public Page<Disco> findAllByGenre(Genre genre, Pageable pageable);
}
