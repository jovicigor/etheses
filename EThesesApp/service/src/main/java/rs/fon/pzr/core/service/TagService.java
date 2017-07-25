package rs.fon.pzr.core.service;

import java.util.Set;

import rs.fon.pzr.model.TagEntity;

public interface TagService {

	TagEntity getTag(Long id);
	
	TagEntity getTagByValue(String value);
	
	Set<TagEntity> getAllTags();
	
	TagEntity addTag(String value);
	
	void removeTag(Long tagId);
	
}
