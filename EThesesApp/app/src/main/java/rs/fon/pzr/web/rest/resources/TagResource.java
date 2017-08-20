package rs.fon.pzr.web.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.domain.model.thesis.Tag;
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

    @GetMapping
    @ResponseBody
    public Set<Tag> getTags() {
        return tagService.getAllTags();
    }

    @DeleteMapping(value = "/{tagID}")
    public void deleteTag(@PathVariable("tagID") Long tagID) {
        tagService.removeTag(tagID);
    }
}
