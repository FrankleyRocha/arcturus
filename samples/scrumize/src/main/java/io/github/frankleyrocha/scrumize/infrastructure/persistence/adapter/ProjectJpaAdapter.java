package io.github.frankleyrocha.scrumize.infrastructure.persistence.adapter;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import io.github.frankleyrocha.arcturus.common.mapper.EntityMapper;
import io.github.frankleyrocha.arcturus.common.spring.persistence.adapter.CrudRepositoryJpaAdapter;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Project;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.ProjectDto;

@Service
public class ProjectJpaAdapter extends CrudRepositoryJpaAdapter<ProjectDto, Project, UUID> {

    public ProjectJpaAdapter(
            JpaRepository<Project, UUID> repo,
            EntityMapper<Project, ProjectDto> mapper) {

        super(repo, mapper);
    }

}
