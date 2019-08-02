package disco.cashback.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import disco.cashback.entity.Cashback;
import disco.commons.constant.Genre;
import disco.commons.constant.Weekday;

@Repository
public interface CashbackRepository extends MongoRepository<Cashback, String> {

	public Cashback findTop1ByGenreAndWeekdayAndStartDateLessThanEqualOrderByStartDateDesc(Genre genre, Weekday weekday, LocalDateTime date);
}
