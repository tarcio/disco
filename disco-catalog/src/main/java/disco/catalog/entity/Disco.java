package disco.catalog.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.Gson;

import disco.commons.constant.Genre;

@Document
public class Disco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String trackId;
	private String name;
	private String album;
	private Genre genre;
	private Double price;

	public Disco() {
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
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (obj.getClass() != getClass())
			return false;

		return id == ((Disco) obj).getId();
	}

	@Override
	public int hashCode() {

		int hash = 5;
		hash = 23 * hash + (id == null ? 0 : id.hashCode());

		return hash;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
