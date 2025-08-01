package edu.mx.unsis.unsiSmile.service.socketNotifications;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ReviewTreatmentService extends NotificationService {

    public ReviewTreatmentService(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    /**
     * Envía un mensaje a todos los suscriptores del tópico:
     *  /topic/treatment-detail/${treatmentDetailId}/patient-id/${patientId}
     */
    public void broadcastReviewTreatmentToStudent(Long treatmentDetailId, String patientId, Object notification) {
        String destination = String.format(
                "/topic/treatment-detail/%d/patient-id/%s",
                treatmentDetailId, patientId);      

        try {
            log.info("Broadcasting review treatment to topic: {}", destination);
            this.sendToTopic(destination, notification);
            log.debug("Payload: {}", notification);
        } catch (Exception ex) {
            log.error("Failed to broadcast to {}: {}", destination, ex.getMessage(), ex);
            throw new RuntimeException("Error broadcasting review treatment", ex);
        }
    }

    /**
     * Envía un mensaje a todos los suscriptores del tópico:
     *  /topic/treatments/proffesor/${professorId}
     */
    public void broadcastReviewTreatmentToProfessor(String professorId, Object notification) {
        String destination = String.format(
                "/topic/treatments/professor/%s", professorId);

        try {
            log.info("Broadcasting review treatment to topic: {}", destination);
            this.sendToTopic(destination, notification);
            log.debug("Payload: {}", notification);
        } catch (Exception ex) {
            log.error("Failed to broadcast to {}: {}", destination, ex.getMessage(), ex);
            throw new RuntimeException("Error broadcasting review treatment to professor", ex);
        }

    }
}