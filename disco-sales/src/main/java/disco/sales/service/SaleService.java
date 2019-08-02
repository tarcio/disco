package disco.sales.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import disco.commons.constant.Weekday;
import disco.sales.entity.Sale;
import disco.sales.entity.SaleItem;
import disco.sales.model.CashbackDTO;
import disco.sales.model.DiscoDTO;
import disco.sales.model.SaleDTO;
import disco.sales.model.SaleItemDTO;
import disco.sales.repository.SaleRepository;

@Service
public class SaleService {

	private static final Logger LOGGER = LogManager.getLogger(SaleService.class);

	private GatewayService gatewayService;
	private SaleRepository saleRepository;

	@Autowired
	public SaleService(GatewayService gatewayService, SaleRepository saleRepository) {

		this.gatewayService = gatewayService;
		this.saleRepository = saleRepository;
	}

	public Sale save(SaleDTO sale) {

		Assert.notNull(sale, "Sale must not be null");
		Assert.notEmpty(sale.getItems(), "Sale must contain items");

		for (SaleItemDTO item : sale.getItems()) {

			Assert.notNull(item.getDiscoId(), String.format("Disco must not be null %s", item));
			Assert.notNull(item.getQuantity(), String.format("Quantity must not be null %s", item));
			Assert.isTrue(item.getQuantity() > 0, String.format("Quantity must be greater than zero %s", item));
		}

		Sale result = new Sale();

		result.setDate(LocalDateTime.now());
		result.setTotal(0D);
		result.setCashbackTotal(0D);

		for (SaleItemDTO item : sale.getItems()) {

			DiscoDTO disco = gatewayService.findDisco(item.getDiscoId());
			CashbackDTO cashback = gatewayService.findCashback(disco.getGenre(), getWeekday());

			SaleItem saleItem = new SaleItem();
			saleItem.setDiscoId(disco.getId());
			saleItem.setValue(disco.getPrice());
			saleItem.setQuantity(item.getQuantity());
			saleItem.setCashbackId(cashback != null ? cashback.getId() : null);
			saleItem.setCashbackValue(cashback != null ? cashback.getValue() : 0);
			saleItem.setTotal(multiply(saleItem.getValue(), saleItem.getQuantity().doubleValue()));
			saleItem.setCashbackTotal(multiply(saleItem.getTotal(), saleItem.getCashbackValue()));

			result.setTotal(result.getTotal() + saleItem.getTotal());
			result.setCashbackTotal(result.getCashbackTotal() + saleItem.getCashbackTotal());

			result.getItems().add(saleItem);
		}

		return save(result);
	}

	public Sale save(Sale sale) {

		LOGGER.debug("Calling saleService.save | args: {}", sale);

		Assert.notNull(sale, "Sale must not be null");
		Assert.notNull(sale.getDate(), "Date must not be null");
		Assert.notNull(sale.getTotal(), "Total must not be null");
		Assert.isTrue(sale.getTotal() > 0, "Total must be greater than zero");
		Assert.notNull(sale.getCashbackTotal(), "Cashback Total must not be null");
		Assert.isTrue(sale.getCashbackTotal() >= 0, "Cashback Total must be greater or equals zero");
		Assert.notEmpty(sale.getItems(), "Sale must contain items");

		for (SaleItem item : sale.getItems()) {

			Assert.notNull(item.getDiscoId(), String.format("Disco must not be null %s", item));
			Assert.notNull(item.getValue(), String.format("Value must not be null %s", item));
			Assert.isTrue(item.getValue() > 0, String.format("Value must be greater than zero %s", item));
			Assert.notNull(item.getQuantity(), String.format("Quantity must not be null %s", item));
			Assert.isTrue(item.getQuantity() > 0, String.format("Quantity must be greater than zero %s", item));
		}

		saleRepository.save(sale);

		LOGGER.debug("saleService.save | return: {}", sale);
		return sale;
	}

	public Page<Sale> findAll(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

		LOGGER.debug("Calling saleService.findAll | args: {}, {}, {}", startDate, endDate, pageable);

		Assert.notNull(startDate, "Start Date must not be null");
		Assert.notNull(endDate, "End Date must not be null");
		Assert.isTrue(startDate.isBefore(endDate), "Start Date must be before End Date");

		Page<Sale> result = saleRepository.findAllByDateBetween(startDate, endDate, pageable);

		LOGGER.debug("saleService.findAll | return {}", result);
		return result;
	}

	public Optional<Sale> findById(String id) {

		LOGGER.debug("Calling saleService.findById | args: {}", id);

		Assert.notNull(id, "Id must not be null");
		Optional<Sale> result = saleRepository.findById(id);

		LOGGER.debug("saleService.findById | return {}", result.orElse(null));
		return result;
	}

	public void deleteAll() {

		LOGGER.debug("Calling saleService.deleteAll");
		saleRepository.deleteAll();
	}

	public Weekday getWeekday() {

		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {

		case Calendar.MONDAY:
			return Weekday.MONDAY;

		case Calendar.TUESDAY:
			return Weekday.TUESDAY;

		case Calendar.WEDNESDAY:
			return Weekday.WEDNESDAY;

		case Calendar.THURSDAY:
			return Weekday.THURSDAY;

		case Calendar.FRIDAY:
			return Weekday.FRIDAY;

		case Calendar.SATURDAY:
			return Weekday.SATURDAY;

		case Calendar.SUNDAY:
			return Weekday.SUNDAY;

		default:
			throw new IllegalArgumentException("Weekday not found");
		}
	}

	private Double multiply(Double x, Double y) {

		return new BigDecimal(x * y).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
	}
}
