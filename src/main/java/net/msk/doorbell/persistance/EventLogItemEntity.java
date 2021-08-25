package net.msk.doorbell.persistance;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class EventLogItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private OffsetDateTime timestamp;

    @Column
    private String eventQualifier;

    public EventLogItemEntity() {
    }

    public EventLogItemEntity(final String eventQualifier) {
        this.timestamp = OffsetDateTime.now();
        this.eventQualifier = eventQualifier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventQualifier() {
        return eventQualifier;
    }

    public void setEventQualifier(String eventQualifier) {
        this.eventQualifier = eventQualifier;
    }
}
