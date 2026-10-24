package com.hospital.appointments.services;

import com.hospital.appointments.clients.DoctorClient;
import com.hospital.appointments.dto.DoctorDTO;
import com.hospital.appointments.dto.clientResponseDTO;
import com.hospital.appointments.exceptions.ServerBusyException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class doctorService {
    DoctorClient doctorClient;
    doctorService(DoctorClient doctorClient) {
        this.doctorClient = doctorClient;
    }

    @RateLimiter(name = "DoctorService",
            fallbackMethod = "DoctorFallback" )
    public clientResponseDTO<DoctorDTO> getDoctorById(long id) {
        System.out.println("getDoctorById");
        return doctorClient.getDoctorById(id);
    }

    private clientResponseDTO<DoctorDTO> DoctorFallback(
            long id,
            Exception exception) {

        throw new ServerBusyException("Doctor service is currently busy");
    }
}
