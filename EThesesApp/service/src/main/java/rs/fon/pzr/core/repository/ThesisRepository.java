package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.core.page.ThesisPage;

import java.util.List;

public interface ThesisRepository {
    Thesis findOne(Long id);

    Thesis save(Thesis thesis);

    List<Thesis> findByUser(UserEntity user);

    List<Thesis> findAll();

    ThesisPage findByNameTagsFieldsAndCourse(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit);

    ThesisPage findByNameTagsFieldsCourseAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    ThesisPage findByNameTagsFieldsAndStudies(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit);

    ThesisPage findByNameTagsFieldsStudiesAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    ThesisPage findByNameTagsAndFields(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit);

    ThesisPage findByNameTagsFieldsAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    Thesis findByName(String name);

    void delete(Thesis thesis);
}
