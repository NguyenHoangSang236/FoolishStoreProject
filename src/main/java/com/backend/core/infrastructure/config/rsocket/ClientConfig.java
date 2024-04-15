package com.backend.core.infrastructure.config.rsocket;

import io.rsocket.RSocket;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class ClientConfig {
    @Bean
    public RSocketRequester getRSocketRequester(){
        RSocketRequester.Builder builder = RSocketRequester.builder();

        return builder
                .rsocketConnector(
                        rSocketConnector -> rSocketConnector.reconnect(
                                Retry.fixedDelay(2, Duration.ofSeconds(2))
                        )
                )
                .rsocketStrategies(rsocketStrategies())
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 7000);
    }

    @Bean
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .routeMatcher(new PathPatternRouteMatcher())
                .build();
    }

    @Bean
    public RSocketMessageHandler rsocketMessageHandler() {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRouteMatcher(new PathPatternRouteMatcher());
        return handler;
    }

}
