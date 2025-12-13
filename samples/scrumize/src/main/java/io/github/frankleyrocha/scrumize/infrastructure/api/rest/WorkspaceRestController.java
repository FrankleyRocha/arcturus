package io.github.frankleyrocha.scrumize.infrastructure.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.frankleyrocha.arcturus.common.repository.CrudRepository;
import io.github.frankleyrocha.arcturus.common.spring.api.rest.CrudRestController;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.WorkspaceDto;

@RestController
@RequestMapping("workspaces")
public class WorkspaceRestController extends CrudRestController<WorkspaceDto, UUID> {

    public WorkspaceRestController(
            CrudRepository<WorkspaceDto, UUID> service) {

        super(service);
    }

}
