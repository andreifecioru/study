package org.afecioru.study.lsb3.ch4.config.oauth2.google;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class YouTubeConfig {
    static final String YOUTUBE_V3_API = "https://www.googleapis.com/youtube/v3";

    @Bean
    public OAuth2AuthorizedClientManager clientManager(
        ClientRegistrationRepository clientRegRepo,
        OAuth2AuthorizedClientRepository authClientRepo
    ) {
        var clientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .refreshToken()
            .clientCredentials()
            .password()
            .build();

        var clientManager = new DefaultOAuth2AuthorizedClientManager(
            clientRegRepo,
            authClientRepo
        );
        clientManager.setAuthorizedClientProvider(clientProvider);

        return clientManager;
    }

    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager clientManager) {
        var oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        oauth2.setDefaultClientRegistrationId("google");

        return WebClient.builder()
            .baseUrl(YOUTUBE_V3_API)
            .apply(oauth2.oauth2Configuration())
            .build();
    }

    @Bean
    public HttpServiceProxyFactory proxyFactory(WebClient oauth2WebClient) {
        return HttpServiceProxyFactory.builder()
            .clientAdapter(WebClientAdapter.forClient(oauth2WebClient))
            .build();
    }

    @Bean
    YouTubeExchange youTubeClient(HttpServiceProxyFactory factory) {
        return factory.createClient(YouTubeExchange.class);
    }
}
