package com.prime.rushhour.domain.appointment.repository;

import com.prime.rushhour.domain.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAppointmentById(Long id);
}
