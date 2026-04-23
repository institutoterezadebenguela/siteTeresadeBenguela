package br.com.instituto.teresa.controller;

import br.com.instituto.teresa.dto.DiscographyTrackResponseDTO;
import br.com.instituto.teresa.service.DiscographyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discography")
public class DiscographyController {

    private final DiscographyService discographyService;

    public DiscographyController(DiscographyService discographyService) {
        this.discographyService = discographyService;
    }

    @GetMapping
    public List<DiscographyTrackResponseDTO> getAllTracks() {
        return discographyService.getAllTracks();
    }
}
