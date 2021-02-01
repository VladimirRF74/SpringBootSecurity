package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.models.User;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
}
