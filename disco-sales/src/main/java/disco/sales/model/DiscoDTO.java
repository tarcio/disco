package disco.sales.model;

import java.io.Serializable;

import com.google.gson.Gson;

import disco.commons.constant.Genre;

public class DiscoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String trackId;
	private String name;
	private String album;
	private Genre genre;
	private Double price;

	public DiscoDTO() {
	}

	public String getId() {

		return id;
	}

	public String getTrackId() {

		return trackId;
	}

	public String getName() {

		return name;
	}

	public String getAlbum() {

		return album;
	}

	public Genre getGenre() {

		return genre;
	}

	public Double getPrice() {

		return price;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setTrackId(String trackId) {

		this.trackId = trackId;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setAlbum(String album) {

		this.album = album;
	}

	public void setGenre(Genre genre) {

		this.genre = genre;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
