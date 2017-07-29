package rs.fon.pzr.core.service;

import java.util.Set;

import rs.fon.pzr.model.thesis.Tag;

public interface TagService {

    Set<Tag> getAllTags();

    Tag addTag(String value);

    void removeTag(Long tagId);

}
