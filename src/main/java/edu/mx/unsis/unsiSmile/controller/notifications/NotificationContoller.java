package edu.mx.unsis.unsiSmile.controller.notifications;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/unsismile/api/v1/notification")
public class NotificationContoller {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/connect")
    public void connectToGame() {
        log.info("Connecting: {}");

        simpMessagingTemplate.convertAndSend("/topic/notifications/");
    }

}
