package com.project.onlinebanking.repository;

import com.project.onlinebanking.entity.AutomaticTellerMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomaticTellerMachineRepository extends JpaRepository<AutomaticTellerMachine, Long> {
    Optional<AutomaticTellerMachine> findByNumber(String number);
}
