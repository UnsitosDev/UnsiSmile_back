package edu.mx.unsis.unsiSmile.service.socketNotifications;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public abstract class NotificationService {
      private final SimpMessagingTemplate messagingTemplate;

    /**
     * Envía un mensaje al tópico de WebSocket especificado.
     * 
     * @param destination Ruta del tópico (por ejemplo: /topic/medical-record/123/patient-id/456)
     * @param payload     Objeto que será enviado como mensaje (se serializa como JSON)
     */
    public void sendToTopic(String destination, Object payload) {
        log.info("Enviando mensaje a WebSocket [{}]: {}", destination, payload);
        messagingTemplate.convertAndSend(destination, payload);
    }

    /**
     * Envía un mensaje a un usuario específico (usando el prefijo `/user`)
     * 
     * @param username    Nombre de usuario
     * @param destination Ruta relativa dentro de `/user/`
     * @param payload     Mensaje
     */
    public void sendToUser(String username, String destination, Object payload) {
        log.info("Enviando mensaje a usuario [{}] en [{}]: {}", username, destination, payload);
        messagingTemplate.convertAndSendToUser(username, destination, payload);
    }
}
