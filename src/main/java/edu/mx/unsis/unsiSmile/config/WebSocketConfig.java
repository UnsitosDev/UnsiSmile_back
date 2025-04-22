package edu.mx.unsis.unsiSmile.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSocketMessageBroker
@Log4j2
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/unsismile/api/v1");
        registry.setUserDestinationPrefix("/user");
        log.info("WebSocket broker configurado con destinos /topic, /queue y prefijo /unsismile/api/v1");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/unsismile/api/v1/notifications")
                .setAllowedOrigins(
                        "http://localhost:8081",
                        "https://unsismile.unsis.edu.mx",
                        "https://132.18.41.181:8081")
                .withSockJS();
        log.info("STOMP endpoints registrados en /unsismile/api/v1/notifications y /unsismile/api/v1/review con SockJS habilitado.");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtAuthChannelInterceptor(jwtTokenProvider));
    }

    // Interceptor desacoplado para claridad y reutilización
    private static class JwtAuthChannelInterceptor implements ChannelInterceptor {

        private final JwtTokenProvider jwtTokenProvider;

        public JwtAuthChannelInterceptor(JwtTokenProvider jwtTokenProvider) {
            this.jwtTokenProvider = jwtTokenProvider;
        }

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

            if ("CONNECT".equals(accessor.getCommand().name())) {
                try {
                    String authHeader = accessor.getFirstNativeHeader("Authorization");

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        log.warn("Conexión WebSocket sin token o con formato inválido.");
                        throw new IllegalArgumentException("Token de autorización no proporcionado o mal formado");
                    }

                    String token = authHeader.substring(7);
                    if (!jwtTokenProvider.validateToken(token)) {
                        log.warn("Token JWT inválido o expirado: {}", token);
                        throw new IllegalArgumentException("Token JWT inválido o expirado");
                    }

                    accessor.setUser(jwtTokenProvider.getAuthentication(token));
                    log.info("Usuario autenticado correctamente para WebSocket: {}", accessor.getUser().getName());

                } catch (Exception ex) {
                    log.error("Error al autenticar conexión WebSocket: {}", ex.getMessage(), ex);
                    throw new IllegalArgumentException("Error de autenticación en conexión WebSocket: " + ex.getMessage());
                }
            }

            return message;
        }
    }
}
