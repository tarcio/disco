package disco.sales.model;

import java.io.Serializable;

import com.google.gson.Gson;

import disco.commons.constant.Genre;
import disco.commons.constant.Weekday;

public class CashbackDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Genre genre;
	private Weekday weekday;
	private Double value;

	public CashbackDTO() {
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

	public void setValue(Double value) {

		this.value = value;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
