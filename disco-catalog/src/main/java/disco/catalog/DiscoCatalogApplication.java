package disco.catalog;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@EnableDiscoveryClient
@SpringBootApplication
public class DiscoCatalogApplication {

	public static void main(String[] args) {

		SpringApplication.run(DiscoCatalogApplication.class, args);
	}

	private static final Logger LOGGER = LogManager.getLogger(DiscoCatalogApplication.class);

	@Value("${spotify.client.id}")
	private String clientId;

	@Value("${spotify.client.secret}")
	private String clientSecret;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public SpotifyApi spotifyApi() {

		LOGGER.debug("Spotify Api | Credentials: clientId {}, clientSecret {}", clientId, clientSecret);
		SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();

		try {

			// Client Credentials Flow
			// https://developer.spotify.com/documentation/general/guides/authorization-guide/#client-credentials-flow

			ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
			ClientCredentials clientCredentials = clientCredentialsRequest.execute();

			spotifyApi.setAccessToken(clientCredentials.getAccessToken());
			LOGGER.debug("Spotify Api | Expires in {}", clientCredentials.getExpiresIn());

		} catch (SpotifyWebApiException | IOException exception) {

			LOGGER.error("Spotify Error", exception);
		}

		return spotifyApi;
	}
}
