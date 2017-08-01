package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.core.page.ThesisPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThesisPageResponse {
    private final int number;
    private final int numberOfElements;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final List<ThesisResponseLevel1> content;

    public ThesisPageResponse(ThesisPage thesisPage) {
        number = thesisPage.getNumber();
        numberOfElements = thesisPage.getNumberOfElements();
        size = thesisPage.getSize();
        totalElements = thesisPage.getTotalElements();
        content = thesisPage.getContent()
                .stream().map(ThesisResponseLevel1::new)
                .collect(Collectors.toList());
        totalPages = thesisPage.getTotalPages();
    }

    public int getNumber() {
        return number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ThesisResponseLevel1> getContent() {
        return content;
    }
}
