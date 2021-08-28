package net.msk.doorbell.persistance;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventLogRepository extends CrudRepository<EventLogItemEntity, Long> {

    List<EventLogItemEntity> findAll(final Sort sort);

}
