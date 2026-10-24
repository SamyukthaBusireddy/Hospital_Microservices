package com.hospital.appointments.controller;

import com.hospital.appointments.dto.AppointmentDTO;
import com.hospital.appointments.dto.AppointmentResponseDTO;
import com.hospital.appointments.dto.GlobalResponseDto;
import com.hospital.appointments.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // POST /api/appointments
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO appointment = appointmentService.createNewAppointment(appointmentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointment);
    }

    // GET /api/appointments
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@RequestParam(required = false) String sortBy) {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments(sortBy);
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(
            @PathVariable Long id
    ){
        AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDTO appointmentDTO
    ){
        AppointmentDTO updatedAppointment = appointmentService.updateAppointmentById(id, appointmentDTO);

        return ResponseEntity.ok(updatedAppointment);
    }


    // PATCH /api/appointments/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updatePartialAppointment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ){
        AppointmentDTO updatedAppointment = appointmentService.updatePartialAppointmentById(id, updates);
        return ResponseEntity.ok(updatedAppointment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        GlobalResponseDto response = new GlobalResponseDto();
        response.setMsg("Appointment successfully deleted");
        return ResponseEntity.ok(response);
    }
}