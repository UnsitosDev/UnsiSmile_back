package edu.mx.unsis.unsiSmile.service.periodontogram;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.SurfaceEvaluationRequest;
import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.SurfaceMeasurementRequest;
import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.ToothEvaluationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.SurfaceEvaluationResponse;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.SurfaceMeasurementResponse;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.ToothEvaluationResponse;
import edu.mx.unsis.unsiSmile.model.periodontograms.PeriodontogramModel;
import edu.mx.unsis.unsiSmile.model.periodontograms.SurfaceEvaluationModel;
import edu.mx.unsis.unsiSmile.model.periodontograms.SurfaceMeasurementModel;
import edu.mx.unsis.unsiSmile.model.periodontograms.ToothEvaluationModel;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.periodontogram.IPeriodontogramRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PeriodontogramService {

    private final IPeriodontogramRepository periodontogramRepository;

    public List<PeriodontogramResponse> findAll() {
        return periodontogramRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<PeriodontogramResponse> findById(Long id) {
        return periodontogramRepository.findById(id)
                .map(this::convertToResponse);
    }

    public PeriodontogramResponse save(PeriodontogramRequest periodontogramRequest) {
        PeriodontogramModel periodontogram = convertToEntity(periodontogramRequest);
        return convertToResponse(periodontogramRepository.save(periodontogram));
    }

    public PeriodontogramResponse update(Long id, PeriodontogramRequest periodontogramRequest) {
        return periodontogramRepository.findById(id).map(periodontogram -> {
            if (periodontogram.getPatient() == null) {
                periodontogram.setPatient(new PatientModel());
            }
            periodontogram.getPatient().setIdPatient(periodontogramRequest.getPatientId());
            periodontogram.setPlaqueIndex(periodontogramRequest.getPlaqueIndex());
            periodontogram.setBleedingIndex(periodontogramRequest.getBleedingIndex());
            periodontogram.setEvaluationDate(LocalDateTime.now());
            periodontogram.setNotes(periodontogramRequest.getNotes());
            return convertToResponse(periodontogramRepository.save(periodontogram));
        }).orElseGet(() -> {
            PeriodontogramModel periodontogram = convertToEntity(periodontogramRequest);
            periodontogram.setIdPeriodontogram(id);
            return convertToResponse(periodontogramRepository.save(periodontogram));
        });
    }

    public void delete(Long id) {
        periodontogramRepository.deleteById(id);
    }

    private PeriodontogramResponse convertToResponse(PeriodontogramModel periodontogram) {
        return PeriodontogramResponse.builder()
                .id(periodontogram.getIdPeriodontogram())
                .patientId(periodontogram.getPatient().getIdPatient())
                .plaqueIndex(periodontogram.getPlaqueIndex())
                .bleedingIndex(periodontogram.getBleedingIndex())
                .evaluationDate(periodontogram.getEvaluationDate())
                .notes(periodontogram.getNotes())
                .toothEvaluations(periodontogram.getToothEvaluations().stream()
                        .map(this::convertToToothEvaluationResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private PeriodontogramModel convertToEntity(PeriodontogramRequest periodontogramRequest) {
        PeriodontogramModel periodontogram = new PeriodontogramModel();
        if (periodontogram.getPatient() == null) {
            periodontogram.setPatient(new PatientModel());
        }
        periodontogram.getPatient().setIdPatient(periodontogramRequest.getPatientId());
        periodontogram.setPlaqueIndex(periodontogramRequest.getPlaqueIndex());
        periodontogram.setBleedingIndex(periodontogramRequest.getBleedingIndex());
        periodontogram.setEvaluationDate(LocalDateTime.now());
        periodontogram.setNotes(periodontogramRequest.getNotes());
        FormSectionModel formSection = new FormSectionModel();
        formSection.setIdFormSection(periodontogramRequest.getFormSection().getId());
        periodontogram.setFormSection(formSection);
        periodontogram.setToothEvaluations(periodontogramRequest.getToothEvaluations().stream()
                .map(te -> convertToToothEvaluationEntity(te, periodontogram))
                .collect(Collectors.toList()));
        return periodontogram;
    }

    private ToothEvaluationResponse convertToToothEvaluationResponse(ToothEvaluationModel toothEvaluation) {
        return ToothEvaluationResponse.builder()
                .id(toothEvaluation.getIdToothEvaluation())
                .idTooth(toothEvaluation.getIdTooth())
                .mobility(toothEvaluation.getMobility())
                .surfaceEvaluations(toothEvaluation.getSurfaceEvaluations().stream()
                        .map(this::convertToSurfaceEvaluationResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private ToothEvaluationModel convertToToothEvaluationEntity(ToothEvaluationRequest toothEvaluationRequest, PeriodontogramModel periodontogram) {
        ToothEvaluationModel toothEvaluation = new ToothEvaluationModel();
        toothEvaluation.setIdTooth(toothEvaluationRequest.getIdTooth());
        toothEvaluation.setMobility(toothEvaluationRequest.getMobility());
        toothEvaluation.setPeriodontogram(periodontogram);
        toothEvaluation.setSurfaceEvaluations(toothEvaluationRequest.getSurfaceEvaluations().stream()
                .map(se -> convertToSurfaceEvaluationEntity(se, toothEvaluation))
                .collect(Collectors.toList()));
        return toothEvaluation;
    }

    private SurfaceEvaluationResponse convertToSurfaceEvaluationResponse(SurfaceEvaluationModel surfaceEvaluation) {
        return SurfaceEvaluationResponse.builder()
                .id(surfaceEvaluation.getIdSurfaceEvaluation())
                .surface(surfaceEvaluation.getSurface().name())
                .surfaceMeasurements(surfaceEvaluation.getSurfaceMeasurements().stream()
                        .map(this::convertToSurfaceMeasurementResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private SurfaceEvaluationModel convertToSurfaceEvaluationEntity(SurfaceEvaluationRequest surfaceEvaluationRequest, ToothEvaluationModel toothEvaluation) {
        SurfaceEvaluationModel surfaceEvaluation = new SurfaceEvaluationModel();
        surfaceEvaluation.setSurface(SurfaceEvaluationModel.Surface.valueOf(surfaceEvaluationRequest.getSurface().name()));
        surfaceEvaluation.setToothEvaluation(toothEvaluation);
        surfaceEvaluation.setSurfaceMeasurements(
            surfaceEvaluationRequest.getSurfaceMeasurements().stream()
                .map(sm -> convertToSurfaceMeasurementEntity(sm, surfaceEvaluation))
                .collect(Collectors.toList())
        );
        return surfaceEvaluation;
    }

    private SurfaceMeasurementResponse convertToSurfaceMeasurementResponse(SurfaceMeasurementModel surfaceMeasurement) {
        return SurfaceMeasurementResponse.builder()
                .id(surfaceMeasurement.getIdSurfaceMeasurement())
                .toothPosition(surfaceMeasurement.getToothPosition().name())
                .pocketDepth(surfaceMeasurement.getPocketDepth())
                .lesionLevel(surfaceMeasurement.getLesionLevel())
                .plaque(surfaceMeasurement.getPlaque())
                .bleeding(surfaceMeasurement.getBleeding())
                .calculus(surfaceMeasurement.getCalculus())
                .build();
    }

    private SurfaceMeasurementModel convertToSurfaceMeasurementEntity(SurfaceMeasurementRequest surfaceMeasurementRequest, SurfaceEvaluationModel surfaceEvaluation) {
        SurfaceMeasurementModel surfaceMeasurement = new SurfaceMeasurementModel();
        surfaceMeasurement.setToothPosition(SurfaceMeasurementModel.ToothPosition.valueOf(surfaceMeasurementRequest.getToothPosition().name()));
        surfaceMeasurement.setPocketDepth(surfaceMeasurementRequest.getPocketDepth());
        surfaceMeasurement.setLesionLevel(surfaceMeasurementRequest.getLesionLevel());
        surfaceMeasurement.setPlaque(surfaceMeasurementRequest.getPlaque());
        surfaceMeasurement.setBleeding(surfaceMeasurementRequest.getBleeding());
        surfaceMeasurement.setCalculus(surfaceMeasurementRequest.getCalculus());
        // Asignar el padre (SurfaceEvaluation) para evitar NullPointerException
        surfaceMeasurement.setSurfaceEvaluation(surfaceEvaluation);
        return surfaceMeasurement;
    }
}
