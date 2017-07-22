package rs.fon.elab.pzr.core.service;

import java.util.Set;

import rs.fon.elab.pzr.core.model.Tag;

public interface TagService {

	Tag getTag(Long id);
	
	Tag getTagByValue(String value);
	
	Set<Tag> getAllTags();
	
	Tag addTag(String value);
	
	void removeTag(Long tagId);
	
}
