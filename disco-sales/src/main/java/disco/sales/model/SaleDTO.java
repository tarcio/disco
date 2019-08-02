package disco.sales.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class SaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SaleItemDTO> items;

	public SaleDTO() {
	}

	public List<SaleItemDTO> getItems() {

		if (items == null)
			items = new ArrayList<SaleItemDTO>();

		return items;
	}

	public void setItems(List<SaleItemDTO> items) {

		this.items = items;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}
}
