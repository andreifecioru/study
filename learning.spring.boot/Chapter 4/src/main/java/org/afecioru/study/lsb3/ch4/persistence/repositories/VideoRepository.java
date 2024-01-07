package org.afecioru.study.lsb3.ch4.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.afecioru.study.lsb3.ch4.persistence.entities.VideoEntity;



public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(
        String partialName,
        String partialDescription
    );

}
