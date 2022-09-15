package com.example.juse.tag.mapper;

import com.example.juse.tag.entity.UserTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "tag.name", source = "tag")
    UserTag toUserTagFrom(String tag);
}
