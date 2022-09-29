package com.example.juse.tag.service;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(
                        () -> new CustomRuntimeException(ExceptionCode.TAG_NOT_FOUND)
                );
    }

}
