package com.hospital.appointments.services;

import com.hospital.appointments.clients.PatientClient;
import com.hospital.appointments.dto.PatientDTO;
import com.hospital.appointments.dto.clientResponseDTO;
import com.hospital.appointments.exceptions.ServerBusyException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class patientService {

    PatientClient patientClient;

    patientService(PatientClient patientClient) {
        this.patientClient = patientClient;
    }

    @RateLimiter(name = "PatientService",
    fallbackMethod = "PatientFallback" )
    public clientResponseDTO<PatientDTO> getPatientById(long id) {
        System.out.println("getPatientById");
        return patientClient.getPatientById(id);
    }

    private clientResponseDTO<PatientDTO> PatientFallback(
            long id,
            Exception exception) {

        throw new ServerBusyException("Patient service is currently busy");
    }
}
