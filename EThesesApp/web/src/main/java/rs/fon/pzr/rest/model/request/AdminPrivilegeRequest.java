package rs.fon.pzr.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminPrivilegeRequest {
    private Boolean isAdmin;

    @JsonCreator
    public AdminPrivilegeRequest(@JsonProperty("admin") boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
