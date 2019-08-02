package disco.cashback.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import disco.cashback.entity.Cashback;
import disco.cashback.repository.CashbackRepository;
import disco.commons.constant.Genre;
import disco.commons.constant.Weekday;

@Service
public class CashbackService {

	private static final Logger LOGGER = LogManager.getLogger(CashbackService.class);

	private CashbackRepository cashbackRepository;

	@Autowired
	public CashbackService(CashbackRepository cashbackRepository) {

		this.cashbackRepository = cashbackRepository;
	}

	public Cashback save(String genre, String weekday, LocalDateTime startDate, Double value) {

		return save(EnumUtils.getEnum(Genre.class, genre), EnumUtils.getEnum(Weekday.class, weekday), startDate, value);
	}

	public Cashback save(Genre genre, Weekday weekday, LocalDateTime startDate, Double value) {

		Cashback cashback = new Cashback();

		cashback.setGenre(genre);
		cashback.setWeekday(weekday);
		cashback.setCreatedDate(LocalDateTime.now());
		cashback.setStartDate(startDate);
		cashback.setValue(value);

		return save(cashback);
	}

	public Cashback save(Cashback cashback) {

		LOGGER.debug("Calling cashbackService.save | args: {}", cashback);

		Assert.notNull(cashback, "Cashback must not be null");
		Assert.notNull(cashback.getGenre(), "Genre must not be null");
		Assert.notNull(cashback.getWeekday(), "Weekday must not be null");
		Assert.notNull(cashback.getCreatedDate(), "Created Date must not be null");
		Assert.notNull(cashback.getStartDate(), "Start Date must not be null");
		Assert.notNull(cashback.getValue(), "Value must not be null");

		cashbackRepository.save(cashback);

		LOGGER.debug("cashbackService.save | return: {}", cashback);
		return cashback;
	}

	public Optional<Cashback> findById(String id) {

		LOGGER.debug("Calling cashbackService.findById | args: {}", id);

		Assert.notNull(id, "Id must not be null");
		Optional<Cashback> result = cashbackRepository.findById(id);

		LOGGER.debug("cashbackService.findById | return {}", result.orElse(null));
		return result;
	}

	public Cashback findByGenreAndWeekday(String genre, String weekday) {

		return findByGenreAndWeekday(EnumUtils.getEnum(Genre.class, genre), EnumUtils.getEnum(Weekday.class, weekday));
	}

	public Cashback findByGenreAndWeekday(Genre genre, Weekday weekday) {

		return findByGenreAndWeekday(genre, weekday, LocalDateTime.now());
	}

	public Cashback findByGenreAndWeekday(Genre genre, Weekday weekday, LocalDateTime date) {

		LOGGER.debug("Calling cashbackService.findByGenreAndWeekday | args: {}, {}, {}", genre, weekday, date);

		Assert.notNull(genre, "Genre must not be null");
		Assert.notNull(weekday, "Weekday must not be null");
		Assert.notNull(date, "Date must not be null");

		Cashback result = cashbackRepository.findTop1ByGenreAndWeekdayAndStartDateLessThanEqualOrderByStartDateDesc(genre, weekday, date);

		LOGGER.debug("cashbackService.findByGenreAndWeekday | return: {}", result);
		return result;
	}

	public List<Cashback> load() {

		LOGGER.debug("Calling cashbackService.load");

		LocalDateTime now = LocalDateTime.now();
		List<Cashback> load = new ArrayList<>();

		// POP
		load.add(save(Genre.POP, Weekday.SUNDAY, now, 0.25));
		load.add(save(Genre.POP, Weekday.MONDAY, now, 0.07));
		load.add(save(Genre.POP, Weekday.TUESDAY, now, 0.06));
		load.add(save(Genre.POP, Weekday.WEDNESDAY, now, 0.02));
		load.add(save(Genre.POP, Weekday.THURSDAY, now, 0.10));
		load.add(save(Genre.POP, Weekday.FRIDAY, now, 0.15));
		load.add(save(Genre.POP, Weekday.SATURDAY, now, 0.20));

		// MPB
		load.add(save(Genre.MPB, Weekday.SUNDAY, now, 0.30));
		load.add(save(Genre.MPB, Weekday.MONDAY, now, 0.05));
		load.add(save(Genre.MPB, Weekday.TUESDAY, now, 0.10));
		load.add(save(Genre.MPB, Weekday.WEDNESDAY, now, 0.15));
		load.add(save(Genre.MPB, Weekday.THURSDAY, now, 0.20));
		load.add(save(Genre.MPB, Weekday.FRIDAY, now, 0.25));
		load.add(save(Genre.MPB, Weekday.SATURDAY, now, 0.30));

		// CLASSIC
		load.add(save(Genre.CLASSIC, Weekday.SUNDAY, now, 0.35));
		load.add(save(Genre.CLASSIC, Weekday.MONDAY, now, 0.03));
		load.add(save(Genre.CLASSIC, Weekday.TUESDAY, now, 0.05));
		load.add(save(Genre.CLASSIC, Weekday.WEDNESDAY, now, 0.08));
		load.add(save(Genre.CLASSIC, Weekday.THURSDAY, now, 0.13));
		load.add(save(Genre.CLASSIC, Weekday.FRIDAY, now, 0.18));
		load.add(save(Genre.CLASSIC, Weekday.SATURDAY, now, 0.25));

		// ROCK
		load.add(save(Genre.ROCK, Weekday.SUNDAY, now, 0.40));
		load.add(save(Genre.ROCK, Weekday.MONDAY, now, 0.10));
		load.add(save(Genre.ROCK, Weekday.TUESDAY, now, 0.15));
		load.add(save(Genre.ROCK, Weekday.WEDNESDAY, now, 0.15));
		load.add(save(Genre.ROCK, Weekday.THURSDAY, now, 0.15));
		load.add(save(Genre.ROCK, Weekday.FRIDAY, now, 0.20));
		load.add(save(Genre.ROCK, Weekday.SATURDAY, now, 0.40));

		LOGGER.debug("cashbackService.load | return: {}", load);
		return load;
	}

	public void deleteAll() {

		LOGGER.debug("Calling cashbackService.deleteAll");
		cashbackRepository.deleteAll();
	}
}
