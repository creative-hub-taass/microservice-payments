package com.creativehub.backend.services.dto;

import java.util.List;
import java.util.UUID;

public interface PublicationDto extends Comparable<PublicationDto> {
    @Override
    default int compareTo(PublicationDto publication) {
        return (int) (getTime() - publication.getTime());
    }

    long getTime();

    UUID getId();

    List<? extends CreationDto> getCreations();
}
