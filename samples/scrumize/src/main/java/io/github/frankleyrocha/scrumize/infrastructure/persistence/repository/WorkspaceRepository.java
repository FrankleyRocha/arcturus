package io.github.frankleyrocha.scrumize.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace,UUID>, JpaSpecificationExecutor<Workspace>{

}
