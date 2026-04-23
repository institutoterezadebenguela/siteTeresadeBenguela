package br.com.instituto.teresa.service;

import br.com.instituto.teresa.domain.Project;
import br.com.instituto.teresa.domain.ProjectFeature;
import br.com.instituto.teresa.dto.ProjectFeatureDTO;
import br.com.instituto.teresa.dto.ProjectRequestDTO;
import br.com.instituto.teresa.dto.ProjectResponseDTO;
import br.com.instituto.teresa.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO dto) {
        if (id == null) {
            throw new RuntimeException("ID do projeto não pode ser nulo");
        }
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o ID: " + id));

        project.setCode(dto.code());
        project.setTitle(dto.title());
        project.setSubtitle(dto.subtitle());
        project.setDescription(dto.description());
        project.setImpact(dto.impact());
        project.setImage(dto.image());

        if (dto.features() != null) {
            project.getFeatures().clear();
            List<ProjectFeature> features = dto.features().stream()
                    .map(f -> new ProjectFeature(f.icon(), f.text()))
                    .collect(Collectors.toList());
            project.getFeatures().addAll(features);
        }

        if (dto.details() != null) {
            project.getDetails().clear();
            project.getDetails().putAll(dto.details());
        }

        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject);
    }

    private ProjectResponseDTO mapToDTO(Project project) {
        List<ProjectFeatureDTO> featureDTOs = project.getFeatures().stream()
            .map(f -> new ProjectFeatureDTO(f.getIcon(), f.getText()))
            .collect(Collectors.toList());

        return new ProjectResponseDTO(
            project.getId(),
            project.getCode(),
            project.getTitle(),
            project.getSubtitle(),
            project.getDescription(),
            project.getImpact(),
            project.getImage(),
            featureDTOs,
            project.getDetails()
        );
    }
}
