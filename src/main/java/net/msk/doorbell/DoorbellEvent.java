package net.msk.doorbell;

import java.time.OffsetDateTime;

public class DoorbellEvent {

    final private String eventQualifier;
    final private String eventDescription;
    final OffsetDateTime timestamp;

    public DoorbellEvent(final String eventQualifier, final String eventDescription) {
        this.eventQualifier = eventQualifier;
        this.eventDescription = eventDescription;
        this.timestamp = OffsetDateTime.now();
    }

    public String getEventQualifier() {
        return eventQualifier;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "DoorbellEvent{" +
                "eventQualifier='" + eventQualifier + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
