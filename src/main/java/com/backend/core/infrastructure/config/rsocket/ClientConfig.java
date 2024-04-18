package com.backend.core.infrastructure.config.rsocket;

import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import io.rsocket.SocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class ClientConfig {
    public static final String FOO_HEADER = "foo-header";

    @Bean
    public RSocketRequester getRSocketRequester(){
        RSocketRequester.Builder builder = RSocketRequester.builder();

        return builder
                .rsocketConnector(
                        rSocketConnector -> rSocketConnector
                                .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                                .acceptor(socketAcceptor())
                )
                .rsocketStrategies(rsocketStrategies())
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .metadataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 7000);
    }

    @Bean
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .routeMatcher(new PathPatternRouteMatcher())
                .metadataExtractorRegistry(r -> r.metadataToExtract(
                        MimeTypeUtils.APPLICATION_JSON,
                        Integer.class,
                        (data, outputMap) -> outputMap.put("commentId", data.toString()))
                )
                .build();
    }

    @Bean
    public SocketAcceptor socketAcceptor() {
        return RSocketMessageHandler.responder(rsocketStrategies(), new CommentRenderInfoDTO());
    }

    @Bean
    public DefaultMetadataExtractor defaultMetadataExtractor() {
        DefaultMetadataExtractor extractor = new DefaultMetadataExtractor(new Jackson2JsonDecoder());
        extractor.metadataToExtract(MimeTypeUtils.APPLICATION_JSON, CommentRenderInfoDTO.class, "foo");

        return extractor;
    }
}
