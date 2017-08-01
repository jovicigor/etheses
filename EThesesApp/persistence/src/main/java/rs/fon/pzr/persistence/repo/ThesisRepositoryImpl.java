package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.ThesisRepository;
import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.persistence.jpa.ThesisJpaRepository;

import java.util.List;

@Repository
public class ThesisRepositoryImpl implements ThesisRepository {

    private final ThesisJpaRepository jpaRepository;

    @Autowired
    public ThesisRepositoryImpl(ThesisJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Thesis findOne(Long id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public Thesis save(Thesis thesis) {
        return jpaRepository.save(thesis);
    }

    @Override
    public List<Thesis> findByUser(UserEntity user) {
        return jpaRepository.findByUser(user);
    }

    @Override
    public List<Thesis> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Page<Thesis> findByNameLikeTagsFieldsAndCoursePagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit) {
        return jpaRepository.findByNameLikeTagsFieldsAndCoursePagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, courseName, fieldValues, fieldsMatchLimit);
    }

    @Override
    public Page<Thesis> findByNameLikeTagsFieldsCourseAndDescriptioinPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        return jpaRepository.findByNameLikeTagsFieldsCourseAndDescriptioinPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, courseName, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
    }

    @Override
    public Page<Thesis> findByNameLikeTagsFieldsAndStudiesPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit) {
        return jpaRepository.findByNameLikeTagsFieldsAndStudiesPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, studiesName, fieldValues, fieldsMatchLimit);
    }

    @Override
    public Page<Thesis> findByNameLikeTagsFieldsStudiesAndDescriptionPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        return jpaRepository.findByNameLikeTagsFieldsStudiesAndDescriptionPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, studiesName, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
    }

    @Override
    public Page<Thesis> findByNameLikeTagsAndFieldsPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit) {
        return jpaRepository.findByNameLikeTagsAndFieldsPagable(pageRequest, thesisName, tagValues, tagsMatchLimit, fieldValues, fieldsMatchLimit);
    }

    @Override
    public Page<Thesis> findByNameLikeTagsFieldsAndDescriptionPagable(PageRequest pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        return jpaRepository.findByNameLikeTagsFieldsAndDescriptionPagable(pageRequest, thesisName, tagValues, tagsMatchLimit, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
    }

    @Override
    public Thesis findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(Thesis thesis) {
        jpaRepository.delete(thesis);
    }
}
