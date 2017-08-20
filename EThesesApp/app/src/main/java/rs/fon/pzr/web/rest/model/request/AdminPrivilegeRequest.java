package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminPrivilegeRequest {
    private final Boolean isAdmin;

    @JsonCreator
    public AdminPrivilegeRequest(@JsonProperty("admin") boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
