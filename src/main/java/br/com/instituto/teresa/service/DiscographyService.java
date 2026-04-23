package br.com.instituto.teresa.service;

import br.com.instituto.teresa.domain.DiscographyTrack;
import br.com.instituto.teresa.dto.DiscographyTrackResponseDTO;
import br.com.instituto.teresa.repository.DiscographyTrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscographyService {

    private final DiscographyTrackRepository repository;

    public DiscographyService(DiscographyTrackRepository repository) {
        this.repository = repository;
    }

    public List<DiscographyTrackResponseDTO> getAllTracks() {
        return repository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    private DiscographyTrackResponseDTO mapToDTO(DiscographyTrack track) {
        return new DiscographyTrackResponseDTO(
            track.getId(),
            track.getCode(),
            track.getTitle(),
            track.getArtist(),
            track.getAudioFile()
        );
    }
}
