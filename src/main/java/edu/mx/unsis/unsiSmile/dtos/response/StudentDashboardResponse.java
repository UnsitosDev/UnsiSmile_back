package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDashboardResponse {
    private Long totalPatients;
    private Long patientsWithDisability;
    private Long patientsRegisteredLastMonth;
    private Map<String, Long> patientsByNationality;
    private Long patientsUnder18;
    private Long patientsBetween18And60;
    private Long patientsOver60;
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
    private Long rejectedTreatments;
    private Long progressingTreatments;
    private Long inReviewTreatments;
}