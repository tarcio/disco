package disco.service.registry.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class RestConfiguration extends WebSecurityConfigurerAdapter {

	private static final String URL_MAPPINGS = "/**";

	@Value("${endpoint.auth.username}")
	private String username;

	@Value("${endpoint.auth.password}")
	private String password;

	@Value("${endpoint.auth.role}")
	private String role;

	@Autowired
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.inMemoryAuthentication().withUser(username).password(encoder().encode(password)).roles(role);
	}

	@Bean
	public PasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable().authorizeRequests().antMatchers(URL_MAPPINGS).authenticated().and().httpBasic();
	}
}
