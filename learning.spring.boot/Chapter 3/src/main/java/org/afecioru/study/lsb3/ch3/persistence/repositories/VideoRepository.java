package org.afecioru.study.lsb3.ch3.persistence.repositories;

import org.afecioru.study.lsb3.ch3.persistence.entities.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByName(String name);

    List<VideoEntity> findByNameContainsIgnoreCase(String partialName);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(String partialDescription);

    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(
      String partialName,
      String partialDescription
    );
}
