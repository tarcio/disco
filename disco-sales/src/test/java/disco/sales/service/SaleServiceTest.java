package disco.sales.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import disco.commons.constant.Genre;
import disco.sales.entity.Sale;
import disco.sales.entity.SaleItem;
import disco.sales.model.DiscoDTO;
import disco.sales.model.SaleDTO;
import disco.sales.model.SaleItemDTO;
import disco.sales.repository.SaleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest {

	@Mock
	private GatewayService gatewayService;

	@Mock
	private SaleRepository saleRepository;

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseNull() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = null;
		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseDate() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseTotal() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseTotalNegative() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(-1D);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseTotalZero() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(0D);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseCashbackTotal() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseCashbackTotalNegative() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(-1D);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoId() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoValue() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoValueNegative() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(-1D);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoValueZero() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(0D);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoQuantity() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(10D);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoQuantityNegative() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(10D);
		saleItem.setQuantity(-1);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveBecauseItemDiscoQuantityZero() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(10D);
		saleItem.setQuantity(0);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		saleService.save(sale);
	}

	@Test
	public void shouldSave() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		when(saleRepository.save(any(Sale.class))).thenReturn(any(Sale.class));

		SaleItem saleItem = new SaleItem();
		saleItem.setDiscoId("discoId");
		saleItem.setValue(10D);
		saleItem.setQuantity(2);

		Sale sale = new Sale();
		sale.setDate(LocalDateTime.now());
		sale.setTotal(100D);
		sale.setCashbackTotal(0D);
		sale.getItems().add(saleItem);

		Sale result = saleService.save(sale);

		Assert.notNull(result, "Sale must not be null");
		Assert.notEmpty(result.getItems(), "Sale must contain items");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseNull() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleDTO saleDTO = null;
		saleService.save(saleDTO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseItems() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleDTO saleDTO = new SaleDTO();
		saleService.save(saleDTO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseItemDisco() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.getItems().add(new SaleItemDTO());

		saleService.save(saleDTO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseItemQuantity() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItemDTO saleItemDTO = new SaleItemDTO();
		saleItemDTO.setDiscoId("discoId");

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.getItems().add(saleItemDTO);

		saleService.save(saleDTO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseItemQuantityNegative() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItemDTO saleItemDTO = new SaleItemDTO();
		saleItemDTO.setDiscoId("discoId");
		saleItemDTO.setQuantity(-1);

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.getItems().add(saleItemDTO);

		saleService.save(saleDTO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSaveDTOBecauseItemQuantityZero() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		SaleItemDTO saleItemDTO = new SaleItemDTO();
		saleItemDTO.setDiscoId("discoId");
		saleItemDTO.setQuantity(0);

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.getItems().add(saleItemDTO);

		saleService.save(saleDTO);
	}

	@Test
	public void shouldSaveDTO() {

		SaleService saleService = new SaleService(gatewayService, saleRepository);

		DiscoDTO discoDTO = getDiscoDTO();
		String discoId = discoDTO.getId();

		when(gatewayService.findDisco(discoId)).thenReturn(discoDTO);
		when(saleRepository.save(any(Sale.class))).thenReturn(any(Sale.class));

		SaleItemDTO saleItemDTO = new SaleItemDTO();
		saleItemDTO.setDiscoId(discoId);
		saleItemDTO.setQuantity(2);

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.getItems().add(saleItemDTO);

		Sale sale = saleService.save(saleDTO);

		Assert.notNull(sale, "Sale must not be null");
		Assert.notEmpty(sale.getItems(), "Sale must contain items");
	}

	private DiscoDTO getDiscoDTO() {

		DiscoDTO discoDTO = new DiscoDTO();
		discoDTO.setId("discoId");
		discoDTO.setTrackId("trackId");
		discoDTO.setName("name");
		discoDTO.setAlbum("album");
		discoDTO.setGenre(Genre.CLASSIC);
		discoDTO.setPrice(10D);

		return discoDTO;
	}
}
