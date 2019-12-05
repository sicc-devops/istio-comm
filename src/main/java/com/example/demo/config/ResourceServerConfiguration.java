package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled=true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);

	@Value("${security.oauth2.resource.jwt.keyValue}")
	private String publicKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(null);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.debug("ResourceServerConfig - HttpSecurity configure");

			http.addFilterBefore(multipartFilter(), CsrfFilter.class)
				.authorizeRequests()
					.anyRequest().authenticated()
				.and().csrf().disable();
	}

	@Bean
	public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("commonsMultipartResolver");
		return multipartFilter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setVerifierKey(publicKey);
		return converter;
	}
	@Bean
	@Primary
	public DefaultTokenServices tokenService() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		//defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
