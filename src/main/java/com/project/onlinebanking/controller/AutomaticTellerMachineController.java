package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.AtmDTO;
import com.project.onlinebanking.entity.AutomaticTellerMachine;
import com.project.onlinebanking.repository.AutomaticTellerMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class AutomaticTellerMachineController {
    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @PostMapping("/create")
    public ResponseEntity<AutomaticTellerMachine> createAtm(@RequestBody AtmDTO atmDTO) {
        AutomaticTellerMachine atm = new AutomaticTellerMachine();
        atm.setNumber(atmDTO.getNumber());
        automaticTellerMachineRepository.save(atm);
        return ResponseEntity.ok(atm);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAtm(@RequestBody AtmDTO atmDTO) {
        AutomaticTellerMachine atm = automaticTellerMachineRepository.findByNumber(atmDTO.getNumber())
                .orElseThrow(() -> new RuntimeException("ATM not found"));
        automaticTellerMachineRepository.delete(atm);
        return ResponseEntity.ok("ATM deleted");
    }
}
