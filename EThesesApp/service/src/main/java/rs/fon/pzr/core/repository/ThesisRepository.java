package rs.fon.pzr.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.user.UserEntity;

import java.util.List;

public interface ThesisRepository {
    Thesis findOne(Long id);

    Thesis save(Thesis thesis);

    List<Thesis> findByUser(UserEntity user);

    List<Thesis> findAll();

    Page<Thesis> findByNameLikeTagsFieldsAndCoursePagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit);

    Page<Thesis> findByNameLikeTagsFieldsCourseAndDescriptioinPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    Page<Thesis> findByNameLikeTagsFieldsAndStudiesPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit);

    Page<Thesis> findByNameLikeTagsFieldsStudiesAndDescriptionPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    Page<Thesis> findByNameLikeTagsAndFieldsPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit);

    Page<Thesis> findByNameLikeTagsFieldsAndDescriptionPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    Thesis findByName(String name);

    void delete(Thesis thesis);
}
