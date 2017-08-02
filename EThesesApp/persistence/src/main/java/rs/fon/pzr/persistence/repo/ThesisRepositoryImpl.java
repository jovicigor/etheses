package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.page.ThesisPage;
import rs.fon.pzr.core.repository.ThesisRepository;
import rs.fon.pzr.core.domain.model.thesis.Thesis;
import rs.fon.pzr.core.domain.model.user.UserEntity;
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
    public ThesisPage findByNameTagsFieldsAndCourse(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsFieldsAndCoursePagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, courseName, fieldValues, fieldsMatchLimit);

        return createThesisPage(page);
    }

    @Override
    public ThesisPage findByNameTagsFieldsCourseAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsFieldsCourseAndDescriptioinPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, courseName, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
        return createThesisPage(page);
    }

    @Override
    public ThesisPage findByNameTagsFieldsAndStudies(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsFieldsAndStudiesPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, studiesName, fieldValues, fieldsMatchLimit);

        return createThesisPage(page);
    }

    @Override
    public ThesisPage findByNameTagsFieldsStudiesAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsFieldsStudiesAndDescriptionPagable(pageRequest, thesisName, tagValues, fieldsMatchLimit, studiesName, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
        return createThesisPage(page);
    }

    @Override
    public ThesisPage findByNameTagsAndFields(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsAndFieldsPagable(pageRequest, thesisName, tagValues, tagsMatchLimit, fieldValues, fieldsMatchLimit);
        return createThesisPage(page);
    }

    @Override
    public ThesisPage findByNameTagsFieldsAndDescription(Integer pageNumber, Integer pageSize, String sortField, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit) {
        PageRequest pageRequest = createPageRequest(sortField, pageNumber, pageSize);
        Page<Thesis> page = jpaRepository.findByNameLikeTagsFieldsAndDescriptionPagable(pageRequest, thesisName, tagValues, tagsMatchLimit, fieldValues, fieldsMatchLimit, descriptionKeys, descriptionKeyLimit);
        return createThesisPage(page);
    }

    @Override
    public Thesis findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public void delete(Thesis thesis) {
        jpaRepository.delete(thesis);
    }

    private static PageRequest createPageRequest(String sortField, int pageNumber, int pageSize) {
        PageRequest pageRequest;
        if (sortField == null || sortField.equals("")) {
            pageRequest = new PageRequest(pageNumber, pageSize, new Sort(
                    Sort.Direction.DESC, "datePosted"));
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize, new Sort(
                    Sort.Direction.DESC, sortField).and(new Sort(
                    Sort.Direction.DESC, "datePosted")));
        }
        return pageRequest;
    }

    private static ThesisPage createThesisPage(Page<Thesis> page) {
        return new ThesisPage(
                page.getContent(), page.getNumber(), page.getNumberOfElements(),
                page.getSize(), page.getTotalElements(), page.getTotalPages()
        );
    }
}
