package edu.mx.unsis.unsiSmile.dtos.response.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreatmentCountResponse {
    @Builder.Default private Long resins = 0L;
    @Builder.Default private Long prophylaxis = 0L;
    @Builder.Default private Long fluorosis = 0L;
    @Builder.Default private Long pitAndFissureSealers = 0L;
    @Builder.Default private Long extractions = 0L;
    @Builder.Default private Long prosthesisRemovable = 0L;
    @Builder.Default private Long removableProsthesis = 0L;
    @Builder.Default private Long prosthodontics = 0L;
    @Builder.Default private Long rootCanals = 0L;
    @Builder.Default private Long scrapedAndSmoothed = 0L;
    @Builder.Default private Long closedAndOpen = 0L;
    @Builder.Default private Long distalWedges = 0L;
    @Builder.Default private Long pulpotomyAndCrowns = 0L;
    @Builder.Default private Long pulpectomyAndCrowns = 0L;
}