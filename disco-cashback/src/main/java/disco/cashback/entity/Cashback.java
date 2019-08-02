package disco.cashback.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.Gson;

import disco.commons.constant.Genre;
import disco.commons.constant.Weekday;

@Document
public class Cashback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Genre genre;
	private Weekday weekday;
	private LocalDateTime createdDate;
	private LocalDateTime startDate;
	private Double value;

	public Cashback() {
	}

	public String getId() {

		return id;
	}

	public Genre getGenre() {

		return genre;
	}

	public Weekday getWeekday() {

		return weekday;
	}

	public LocalDateTime getCreatedDate() {

		return createdDate;
	}

	public LocalDateTime getStartDate() {

		return startDate;
	}

	public Double getValue() {

		return value;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setGenre(Genre genre) {

		this.genre = genre;
	}

	public void setWeekday(Weekday weekday) {

		this.weekday = weekday;
	}

	public void setCreatedDate(LocalDateTime createdDate) {

		this.createdDate = createdDate;
	}

	public void setStartDate(LocalDateTime startDate) {

		this.startDate = startDate;
	}

	public void setValue(Double value) {

		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (obj.getClass() != getClass())
			return false;

		return id == ((Cashback) obj).getId();
	}

	@Override
	public int hashCode() {

		int hash = 3;
		hash = 19 * hash + (id == null ? 0 : id.hashCode());

		return hash;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
