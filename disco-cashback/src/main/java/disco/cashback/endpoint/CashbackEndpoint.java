package disco.cashback.endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import disco.cashback.entity.Cashback;
import disco.cashback.service.CashbackService;

@RestController
@RequestMapping("cashback")
public class CashbackEndpoint {

	private static final Logger LOGGER = LogManager.getLogger(CashbackEndpoint.class);

	private CashbackService cashbackService;

	@Autowired
	public CashbackEndpoint(CashbackService cashbackService) {

		this.cashbackService = cashbackService;
	}

	@GetMapping("/save/{genre}/{weekday}/{value}")
	public ResponseEntity<Cashback> save(@PathVariable String genre, @PathVariable String weekday, @PathVariable Double value) {

		try {

			LOGGER.debug("Calling cashbackEndpoint.save | args: {}, {}, {}", genre, weekday, value);
			Cashback cashback = cashbackService.save(genre, weekday, LocalDateTime.now(), value);

			LOGGER.debug("cashbackEndpoint.save | return: {}", cashback);
			return ResponseEntity.created(null).body(cashback);

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Cashback> findById(@PathVariable String id) {

		try {

			LOGGER.debug("Calling cashbackEndpoint.findById | args: {}", id);
			Optional<Cashback> cashback = cashbackService.findById(id);

			LOGGER.debug("cashbackEndpoint.findById | return: {}", cashback.orElse(null));
			return ResponseEntity.of(cashback);

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/find/{genre}/{weekday}")
	public ResponseEntity<Cashback> findByGenreAndWeekday(@PathVariable String genre, @PathVariable String weekday) {

		try {

			LOGGER.debug("Calling cashbackEndpoint.findByGenreAndWeekday | args: {}, {}", genre, weekday);
			Cashback cashback = cashbackService.findByGenreAndWeekday(genre, weekday);

			LOGGER.debug("cashbackEndpoint.findByGenreAndWeekday | return: {}", cashback);
			return ResponseEntity.of(Optional.ofNullable(cashback));

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/load")
	public ResponseEntity<List<Cashback>> load() {

		try {

			LOGGER.debug("Calling cashbackEndpoint.load");
			List<Cashback> load = cashbackService.load();

			LOGGER.debug("cashbackEndpoint.load | return: {}", load);
			return ResponseEntity.created(null).body(load);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/deleteAll")
	public ResponseEntity<String> deleteAll() {

		try {

			LOGGER.debug("Calling cashbackEndpoint.deleteAll");
			cashbackService.deleteAll();

			return ResponseEntity.ok().body("All documents deleted!");

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}
}
