package net.msk.doorbell.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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

    @Column
    private String eventDescription;

    public EventLogItemEntity() {
    }

    public EventLogItemEntity(final String eventQualifier, final String eventDescription) {
        this.timestamp = OffsetDateTime.now();
        this.eventQualifier = eventQualifier;
        this.eventDescription = eventDescription;
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
