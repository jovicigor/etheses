package rs.fon.elab.pzr.rest.resources;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.elab.pzr.core.model.Tag;
import rs.fon.elab.pzr.core.service.TagService;

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
