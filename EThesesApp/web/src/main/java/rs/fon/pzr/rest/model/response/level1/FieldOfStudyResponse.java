package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.model.FieldOfStudyEntity;

public class FieldOfStudyResponse {

    private final Long id;
    private final String name;

    public FieldOfStudyResponse(FieldOfStudyEntity studiesField) {
        id = studiesField.getId();
        name = studiesField.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
