package net.msk.doorbell.persistance;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventLogRepository extends CrudRepository<EventLogItemEntity, Long> {

    @Override
    List<EventLogItemEntity> findAll();

}
