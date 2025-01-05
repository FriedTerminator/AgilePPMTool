package io.nikitacherepanov.ppmtool.repositories;

import io.nikitacherepanov.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
