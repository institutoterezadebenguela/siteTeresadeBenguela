package br.com.instituto.teresa.controller;

import br.com.instituto.teresa.dto.VolunteerRequestDTO;
import br.com.instituto.teresa.service.VolunteerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createVolunteer(@Valid @RequestBody VolunteerRequestDTO volunteerDTO) {
        volunteerService.processVolunteer(volunteerDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Candidatura enviada com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
