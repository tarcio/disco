package disco.catalog.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import disco.catalog.entity.Disco;
import disco.catalog.repository.DiscoRepository;
import disco.commons.constant.Genre;

@Service
public class DiscoService {

	private static final Logger LOGGER = LogManager.getLogger(DiscoService.class);

	private SpotifyApi spotifyApi;
	private DiscoRepository discoRepository;

	@Autowired
	public DiscoService(SpotifyApi spotifyApi, DiscoRepository discoRepository) {

		this.spotifyApi = spotifyApi;
		this.discoRepository = discoRepository;
	}

	public Disco save(Disco disco) {

		LOGGER.debug("Calling discoService.save | args: {}", disco);

		Assert.notNull(disco, "Disco must not be null");
		Assert.notNull(disco.getTrackId(), "Genre must not be null");
		Assert.notNull(disco.getName(), "Name must not be null");
		Assert.notNull(disco.getGenre(), "Genre must not be null");
		Assert.notNull(disco.getPrice(), "Price must not be null");
		Assert.isTrue(disco.getPrice() > 0, "Price must greater than zero");

		discoRepository.save(disco);

		LOGGER.debug("discoService.save | return: {}", disco);
		return disco;
	}

	public Optional<Disco> findById(String id) {

		LOGGER.debug("Calling discoService.findById | args: {}", id);

		Assert.notNull(id, "Id must not be null");
		Optional<Disco> result = discoRepository.findById(id);

		LOGGER.debug("discoService.findById | return {}", result.orElse(null));
		return result;
	}

	public Page<Disco> findAllPageable(Genre genre, Pageable pageable) {

		return genre == null ? findAll(pageable) : findAll(genre, pageable);
	}

	public Page<Disco> findAll(Pageable pageable) {

		LOGGER.debug("Calling discoService.findAll | args: {}", pageable);
		Page<Disco> result = discoRepository.findAll(pageable);

		LOGGER.debug("discoService.findAll | return {}", result.getContent());
		return result;
	}

	public Page<Disco> findAll(Genre genre, Pageable pageable) {

		LOGGER.debug("Calling discoService.findAll | args: {}, {}", genre, pageable);

		Assert.notNull(genre, "Genre must not be null");
		Page<Disco> result = discoRepository.findAllByGenre(genre, pageable);

		LOGGER.debug("discoService.findAll | return {}", result);
		return result;
	}

	public List<Disco> load() throws Exception {

		LOGGER.debug("Calling discoService.load");
		List<Disco> load = new ArrayList<>();

		for (Genre genre : Genre.values()) {

			SearchTracksRequest request = spotifyApi.searchTracks("genre:".concat(genre.name())).limit(50).build();
			Track[] tracks = request.execute().getItems();

			for (Track track : tracks) {

				Disco disco = new Disco();
				disco.setTrackId(track.getId());
				disco.setName(track.getName());
				disco.setAlbum(track.getAlbum() != null ? track.getAlbum().getName() : null);
				disco.setGenre(genre);
				disco.setPrice(new BigDecimal(Math.random() * 200 + 50).setScale(2, RoundingMode.HALF_EVEN).doubleValue()); // $50 ~ $250

				load.add(save(disco));
			}
		}

		LOGGER.debug("discoService.load | return: {}", load);
		return load;
	}

	public void deleteAll() {

		LOGGER.debug("Calling discoService.deleteAll");
		discoRepository.deleteAll();
	}
}
