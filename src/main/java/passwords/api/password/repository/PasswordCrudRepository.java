package passwords.api.password.repository;

import org.springframework.data.repository.CrudRepository;
import passwords.api.password.entity.Password;

public interface PasswordCrudRepository extends CrudRepository<Password, Long> {
}
