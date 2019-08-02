package disco.sales.model;

import java.io.Serializable;

import com.google.gson.Gson;

public class SaleItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String discoId;
	private Integer quantity;

	public SaleItemDTO() {
	}

	public String getDiscoId() {

		return discoId;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setDiscoId(String discoId) {

		this.discoId = discoId;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
