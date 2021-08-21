package net.msk.doorbell;

import java.time.OffsetDateTime;

public class DoorbellEvent {

    final private String eventSource;
    final private String eventDescription;
    final OffsetDateTime timestamp;

    public DoorbellEvent(final String eventSourceQualifier, final String eventDescription) {
        this.eventSource = eventSourceQualifier;
        this.eventDescription = eventDescription;
        this.timestamp = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return "DoorbellEvent{" +
                "eventSource='" + eventSource + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
