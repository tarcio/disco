package disco.sales.entity;

import java.io.Serializable;

import com.google.gson.Gson;

public class SaleItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String discoId;
	private Double value;
	private Integer quantity;
	private String cashbackId;
	private Double cashbackValue;
	private Double total;
	private Double cashbackTotal;

	public SaleItem() {
	}

	public String getDiscoId() {

		return discoId;
	}

	public Double getValue() {

		return value;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public String getCashbackId() {

		return cashbackId;
	}

	public Double getCashbackValue() {

		return cashbackValue;
	}

	public Double getTotal() {

		return total;
	}

	public Double getCashbackTotal() {

		return cashbackTotal;
	}

	public void setDiscoId(String discoId) {

		this.discoId = discoId;
	}

	public void setValue(Double value) {

		this.value = value;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	public void setCashbackId(String cashbackId) {

		this.cashbackId = cashbackId;
	}

	public void setCashbackValue(Double cashbackValue) {

		this.cashbackValue = cashbackValue;
	}

	public void setTotal(Double total) {

		this.total = total;
	}

	public void setCashbackTotal(Double cashbackTotal) {

		this.cashbackTotal = cashbackTotal;
	}

	@Override
	public int hashCode() {

		int hash = 11;
		hash = 31 * hash + (discoId == null ? 0 : discoId.hashCode());
		hash = 37 * hash + (value == null ? 0 : value.hashCode());
		hash = 41 * hash + (quantity == null ? 0 : quantity.hashCode());
		hash = 43 * hash + (cashbackId == null ? 0 : cashbackId.hashCode());
		hash = 47 * hash + (cashbackValue == null ? 0 : cashbackValue.hashCode());

		return hash;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
