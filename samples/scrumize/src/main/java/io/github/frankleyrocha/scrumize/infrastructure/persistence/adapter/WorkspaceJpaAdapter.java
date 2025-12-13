package io.github.frankleyrocha.scrumize.infrastructure.persistence.adapter;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import io.github.frankleyrocha.arcturus.common.mapper.EntityMapper;
import io.github.frankleyrocha.arcturus.common.spring.persistence.adapter.CrudRepositoryJpaAdapter;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Workspace;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.WorkspaceDto;

@Service
public class WorkspaceJpaAdapter extends CrudRepositoryJpaAdapter<WorkspaceDto, Workspace, UUID> {

    public WorkspaceJpaAdapter(
            JpaRepository<Workspace, UUID> repo,
            EntityMapper<Workspace, WorkspaceDto> mapper) {

        super(repo, mapper);
    }

}
