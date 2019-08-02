package disco.sales.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;

import disco.commons.constant.Genre;
import disco.commons.constant.Weekday;
import disco.sales.model.CashbackDTO;
import disco.sales.model.DiscoDTO;

@Service
public class GatewayService {

	private static final Logger LOGGER = LogManager.getLogger(GatewayService.class);

	@Value("${microservice.disco.auth.username}")
	private String microserviceDiscoUsername;

	@Value("${microservice.disco.auth.password}")
	private String microserviceDiscoPassword;

	@Value("${microservice.cashback.auth.username}")
	private String microserviceCashbackUsername;

	@Value("${microservice.cashback.auth.password}")
	private String microserviceCashbackPassword;

	private EurekaClient eurekaClient;

	@Autowired
	public GatewayService(EurekaClient eurekaClient) {

		this.eurekaClient = eurekaClient;
	}

	public DiscoDTO findDisco(String discoId) {

		String url = eurekaClient.getNextServerFromEureka("DISCO-CATALOG", false).getHomePageUrl() + "catalog/find/{id}";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(microserviceDiscoUsername, microserviceDiscoPassword);

		HttpEntity<DiscoDTO> httpEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();

		try {

			LOGGER.debug("Calling gatewayService.findDisco | args: {}", url);
			ResponseEntity<DiscoDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, DiscoDTO.class, discoId);

			DiscoDTO result = responseEntity.getBody();
			LOGGER.debug("gatewayService.findDisco | return: {}", result);

			return responseEntity.getBody();

		} catch (HttpClientErrorException exception) {

			throw new IllegalArgumentException(String.format("Disco not found %s", discoId));
		}
	}

	public CashbackDTO findCashback(Genre genre, Weekday weekday) {

		try {

			String url = eurekaClient.getNextServerFromEureka("DISCO-CASHBACK", false).getHomePageUrl() + "cashback/find/{genre}/{weekday}";

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setBasicAuth(microserviceCashbackUsername, microserviceCashbackPassword);

			HttpEntity<CashbackDTO> httpEntity = new HttpEntity<>(httpHeaders);
			RestTemplate restTemplate = new RestTemplate();

			LOGGER.debug("Calling gatewayService.findCashback | args: {}", url);
			ResponseEntity<CashbackDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CashbackDTO.class, genre, weekday);

			CashbackDTO result = responseEntity.getBody();
			LOGGER.debug("gatewayService.findCashback | return: {}", result);

			return responseEntity.getBody();

		} catch (RuntimeException exception) {

			return null;
		}
	}
}
