package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.appointment.mapper.AppointmentMapper;
import com.prime.rushhour.domain.appointment.repository.AppointmentRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentResponse save(AppointmentRequest appointmentRequest) {
        var appointment = appointmentMapper.toEntity(appointmentRequest);
        appointment.setEndDate(appointment.getStartDate().plusMinutes(appointment.getActivity().getDuration().toMinutes()));
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse getById(Long id) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public Page<AppointmentResponse> getAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest appointmentRequest) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

        appointment.setEndDate(appointment.getStartDate().plusMinutes(appointment.getActivity().getDuration().toMinutes()));
        appointmentMapper.update(appointment, appointmentRequest);
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }
}