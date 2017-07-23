package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.persistence.model.Tag;
import rs.fon.pzr.core.service.TagService;

import java.util.Set;

@RestController
@RequestMapping(value = "/tags")
public class TagResource {

    private final TagService tagService;

    @Autowired
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<Tag> getTags() {
        return tagService.getAllTags();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{tagID}")
    public void deleteTag(@PathVariable("tagID") Long tagID) {
        tagService.removeTag(tagID);
    }
}
