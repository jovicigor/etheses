package rs.fon.elab.pzr.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.model.Tag;
import rs.fon.elab.pzr.core.service.TagService;
import rs.fon.elab.pzr.rest.model.response.old.CourseResponse;
import rs.fon.elab.pzr.rest.model.util.RestFactory;

@RestController
@RequestMapping(value = "/tags")
public class TagResource {

	private TagService tagService;

	// READ
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Set<Tag> getTags() {
		return tagService.getAllTags();
	}
	
	// DELETE
	@RequestMapping(method = RequestMethod.DELETE, value = "/{tagID}")
	public void deleteTag(@PathVariable("tagID") Long tagID) {
		tagService.removeTag(tagID);
	}	

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
}
