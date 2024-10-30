package passwords.api.device.repository;

import org.springframework.data.repository.CrudRepository;
import passwords.api.device.entity.Device;

public interface DeviceCrudRepository extends CrudRepository<Device, Long> {
}
