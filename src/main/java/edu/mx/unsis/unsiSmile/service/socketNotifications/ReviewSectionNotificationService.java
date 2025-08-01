package edu.mx.unsis.unsiSmile.service.socketNotifications;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;

@Service
@Log4j2
public class ReviewSectionNotificationService extends NotificationService {

    public ReviewSectionNotificationService(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    /**
     * Envía un mensaje a todos los suscriptores del tópico:
     * /topic/feedback/medical-record/{medicalRecordId}/patient/{patientId}
     */
    public void broadcastReviewStatus(Long medicalRecordId,
            String patientId,
            ReviewStatusResponse notification) {
        String destination = String.format(
                "/topic/feedback/medical-record/%d/patient/%s",
                medicalRecordId, patientId);

        try {
            log.info("Broadcasting review status to topic: {}", destination);
            this.sendToTopic(destination, notification);
            log.debug("Payload: {}", notification);
        } catch (Exception ex) {
            log.error("Failed to broadcast to {}: {}", destination, ex.getMessage(), ex);
            throw new AppException(
                    ResponseMessages.NOTIFICATION_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }
}
