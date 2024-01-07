package org.afecioru.study.lsb3.ch4.services;

import java.util.List;

import lombok.Data;

import org.afecioru.study.lsb3.ch4.controllers.dto.SearchDTO;
import org.afecioru.study.lsb3.ch4.persistence.repositories.VideoRepository;
import org.springframework.stereotype.Service;

import org.afecioru.study.lsb3.ch4.persistence.entities.VideoEntity;
import org.afecioru.study.lsb3.ch4.controllers.dto.VideoDTO;


@Data
@Service
public class VideoService {
    private final VideoRepository repository;

    public List<VideoDTO> getVideos() {
        return repository.findAll().stream()
            .map(VideoService::videoEntityToDTO)
            .toList();
    }

    public void createVideo(VideoDTO videoDTO) {
        repository.save(videoDTOToEntity(videoDTO));
    }

    public List<VideoDTO> searchVideo(SearchDTO searchDTO) {
        return repository.findByNameContainsOrDescriptionContainsAllIgnoreCase(
                searchDTO.term(),
                searchDTO.term()
            )
            .stream()
            .map(VideoService::videoEntityToDTO)
            .toList();
    }

    private static VideoDTO videoEntityToDTO(VideoEntity videoEntity) {
        return new VideoDTO(
            videoEntity.getName(),
            videoEntity.getDescription()
        );
    }

    private static VideoEntity videoDTOToEntity(VideoDTO videoDTO) {
        return new VideoEntity(
            null,
            videoDTO.name(),
            videoDTO.description()
        );
    }
}
