package rs.fon.pzr.core.page;

import rs.fon.pzr.model.thesis.Thesis;

import java.util.Collection;

public class ThesisPage {

    private final Collection<Thesis> content;
    private int number;
    private final int numberOfElements;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public ThesisPage(Collection<Thesis> content, int number, int numberOfElements, int size, long totalElements, int totalPages) {
        this.content = content;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public Collection<Thesis> getContent() {
        return content;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}
