package io.nikitacherepanov.ppmtool.repositories;

import io.nikitacherepanov.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);

    @Query("SELECT p FROM Project p JOIN FETCH p.user WHERE p.projectIdentifier = :projectIdentifier")
    Optional<Project> findByProjectIdentifierWithUser(@Param("projectIdentifier") String projectIdentifier);

}
