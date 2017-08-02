package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.core.domain.model.thesis.Tag;

public class TagResponse {

    private final Long id;

    private final String value;

    public TagResponse(Tag tag) {
        id = tag.getId();
        value = tag.getValue();
    }

    public Long getId() {
        return id;
    }


    public String getValue() {
        return value;
    }
}
