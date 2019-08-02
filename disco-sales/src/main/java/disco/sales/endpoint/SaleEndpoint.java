package disco.sales.endpoint;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import disco.sales.entity.Sale;
import disco.sales.model.SaleDTO;
import disco.sales.service.SaleService;

@RestController
@RequestMapping("sale")
public class SaleEndpoint {

	private static final Logger LOGGER = LogManager.getLogger(SaleEndpoint.class);

	private SaleService saleService;

	@Autowired
	public SaleEndpoint(SaleService saleService) {

		this.saleService = saleService;
	}

	@PostMapping("/save")
	public ResponseEntity<Sale> save(@RequestBody SaleDTO sale) {

		try {

			LOGGER.debug("Calling saleEndpoint.save | args: {}", sale);
			Sale result = saleService.save(sale);

			LOGGER.debug("saleEndpoint.save | return: {}", result);
			return ResponseEntity.created(null).body(result);

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Sale> findById(@PathVariable String id) {

		try {

			LOGGER.debug("Calling saleEndpoint.findById | args: {}", id);
			Optional<Sale> sale = saleService.findById(id);

			LOGGER.debug("saleEndpoint.findById | return: {}", sale.orElse(null));
			return ResponseEntity.of(sale);

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/find")
	public Page<Sale> find(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size,
			@RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
	) {

		try {

			LOGGER.debug("Calling saleEndpoint.find | args: {}, {}, {}, {}", page, size, startDate, endDate);
			Page<Sale> salePage = saleService.findAll(startDate, endDate, PageRequest.of(page, size, Sort.Direction.DESC, "date"));

			LOGGER.debug("saleEndpoint.find | return: {}", salePage);
			return salePage;

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/deleteAll")
	public ResponseEntity<String> deleteAll() {

		try {

			LOGGER.debug("Calling saleEndpoint.deleteAll");
			saleService.deleteAll();

			return ResponseEntity.ok().body("All documents deleted!");

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}
}
