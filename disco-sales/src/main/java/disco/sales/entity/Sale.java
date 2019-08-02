package disco.sales.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.Gson;

@Document
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private LocalDateTime date;
	private Double total;
	private Double cashbackTotal;
	private List<SaleItem> items;

	public Sale() {
	}

	public String getId() {

		return id;
	}

	public LocalDateTime getDate() {

		return date;
	}

	public Double getTotal() {

		return total;
	}

	public Double getCashbackTotal() {

		return cashbackTotal;
	}

	public List<SaleItem> getItems() {

		if (items == null)
			items = new ArrayList<>();

		return items;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setDate(LocalDateTime date) {

		this.date = date;
	}

	public void setTotal(Double total) {

		this.total = total;
	}

	public void setCashbackTotal(Double cashbackTotal) {

		this.cashbackTotal = cashbackTotal;
	}

	public void setItems(List<SaleItem> items) {

		this.items = items;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (obj.getClass() != getClass())
			return false;

		return id == ((Sale) obj).getId();
	}

	@Override
	public int hashCode() {

		int hash = 7;
		hash = 29 * hash + (id == null ? 0 : id.hashCode());

		return hash;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
