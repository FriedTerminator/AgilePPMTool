package io.nikitacherepanov.ppmtool.repositories;

import io.nikitacherepanov.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
}
