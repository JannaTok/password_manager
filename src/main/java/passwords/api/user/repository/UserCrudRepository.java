package passwords.api.user.repository;

import org.springframework.data.repository.CrudRepository;
import passwords.api.user.entity.User;

import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
