package disco.catalog.endpoint;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import disco.catalog.entity.Disco;
import disco.catalog.service.DiscoService;
import disco.commons.constant.Genre;

@RestController
@RequestMapping("catalog")
public class CatalogEndpoint {

	private static final Logger LOGGER = LogManager.getLogger(CatalogEndpoint.class);

	private DiscoService discoService;

	@Autowired
	public CatalogEndpoint(DiscoService discoService) {

		this.discoService = discoService;
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Disco> findById(@PathVariable String id) {

		try {

			LOGGER.debug("Calling catalogEndpoint.findById | args: {}", id);
			Optional<Disco> disco = discoService.findById(id);

			LOGGER.debug("catalogEndpoint.findById | return: {}", disco.orElse(null));
			return ResponseEntity.of(disco);

		} catch (IllegalArgumentException exception) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/find")
	public Page<Disco> find(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size,
			@RequestParam(value = "genre", required = false) Genre genre
	) {

		try {

			LOGGER.debug("Calling catalogEndpoint.find | args: {}, {}, {}", page, size, genre);
			Page<Disco> discoPage = discoService.findAllPageable(genre, PageRequest.of(page, size, Sort.Direction.ASC, "name"));

			LOGGER.debug("catalogEndpoint.find | return: {}", discoPage);
			return discoPage;

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/load")
	public ResponseEntity<List<Disco>> load() {

		try {

			LOGGER.debug("Calling catalogEndpoint.load");
			List<Disco> load = discoService.load();

			LOGGER.debug("catalogEndpoint.load | return: {}", load);
			return ResponseEntity.created(null).body(load);

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}

	@GetMapping("/deleteAll")
	public ResponseEntity<String> deleteAll() {

		try {

			LOGGER.debug("Calling catalogEndpoint.deleteAll");
			discoService.deleteAll();

			return ResponseEntity.ok().body("All documents deleted!");

		} catch (Exception exception) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);

		}
	}
}
