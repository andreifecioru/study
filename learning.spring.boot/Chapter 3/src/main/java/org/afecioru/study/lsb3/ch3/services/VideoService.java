package org.afecioru.study.lsb3.ch3.services;

import lombok.Data;
import org.afecioru.study.lsb3.ch3.persistence.entities.VideoEntity;
import org.afecioru.study.lsb3.ch3.persistence.repositories.VideoRepository;
import org.afecioru.study.lsb3.ch3.services.dto.SearchDTO;
import org.afecioru.study.lsb3.ch3.services.dto.VideoDTO;
import org.afecioru.study.lsb3.ch3.services.dto.MultiFieldSearchDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Data
@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public List<VideoDTO> getVideos() {
        return videoRepository.findAll().stream()
            .map(videoEntity ->
                new VideoDTO(videoEntity.getName(), videoEntity.getDescription())
            )
            .toList();
    }

    public VideoDTO addVideo(VideoDTO videoDTO) {
        var videoEntity = videoRepository.save(new VideoEntity(null, videoDTO.name(), videoDTO.description()));
        return new VideoDTO(
            videoEntity.getName(),
            videoDTO.description()
        );
    }

    public List<VideoDTO> multiFieldSearch(MultiFieldSearchDTO multiFieldSearchDTO) {
        if (StringUtils.hasLength(multiFieldSearchDTO.name()) &&
            StringUtils.hasText(multiFieldSearchDTO.description())) {

            return videoRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase(
                multiFieldSearchDTO.name(),
                multiFieldSearchDTO.description()
            ).stream().map(VideoService::videoEntityToDTO).toList();
        }

        if (StringUtils.hasText(multiFieldSearchDTO.name())) {
            return videoRepository.findByNameContainsIgnoreCase(
                multiFieldSearchDTO.name()
            ).stream().map(VideoService::videoEntityToDTO).toList();
        }

        if (StringUtils.hasText(multiFieldSearchDTO.description())) {
            return videoRepository.findByDescriptionContainsIgnoreCase(
                multiFieldSearchDTO.name()
            ).stream().map(VideoService::videoEntityToDTO).toList();
        }

        return Collections.emptyList();
    }

    public List<VideoDTO> search(SearchDTO searchDTO) {
        var probe = new VideoEntity();
        if (StringUtils.hasText(searchDTO.term())) {
            probe.setName(searchDTO.term());
            probe.setDescription(searchDTO.term());
        }

        var example = Example.of(probe,
            ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return videoRepository.findAll(example)
            .stream().map(VideoService::videoEntityToDTO).toList();
    }

    private static VideoDTO videoEntityToDTO(VideoEntity videoEntity) {
        return new VideoDTO(
            videoEntity.getName(),
            videoEntity.getDescription()
        );
    }
}
