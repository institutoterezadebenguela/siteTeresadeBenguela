package br.com.instituto.teresa.service;

import br.com.instituto.teresa.domain.Volunteer;
import br.com.instituto.teresa.dto.VolunteerRequestDTO;
import br.com.instituto.teresa.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void processVolunteer(VolunteerRequestDTO dto) {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(dto.name());
        volunteer.setEmail(dto.email());
        volunteer.setPhone(dto.phone());
        volunteer.setAge(dto.age());
        volunteer.setMotivation(dto.motivation());
        
        volunteerRepository.save(volunteer);
    }
}
