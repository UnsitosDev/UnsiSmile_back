package edu.mx.unsis.unsiSmile.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConstants {
    @Value("${api.uris.download.files}")
    private String downloadFilesUrl;
}
