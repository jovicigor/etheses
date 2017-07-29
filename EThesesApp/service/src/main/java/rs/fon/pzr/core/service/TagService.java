package rs.fon.pzr.core.service;

import java.util.Set;

import rs.fon.pzr.model.thesis.TagEntity;

public interface TagService {

    Set<TagEntity> getAllTags();

    TagEntity addTag(String value);

    void removeTag(Long tagId);

}
