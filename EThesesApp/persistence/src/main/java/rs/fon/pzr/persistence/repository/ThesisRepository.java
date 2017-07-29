package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.user.UserEntity;

public interface ThesisRepository extends PagingAndSortingRepository<Thesis, Long>  {
	
	List<Thesis> findAll();
	List<Thesis> findByUser(UserEntity user);
	Thesis findByName(String name);
	
	
	@Query("SELECT distinct t from ThesisEntity t where t.name like ?1 and ?3  <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?5  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?4)"
			+ ")")
	Page<Thesis> findByNameLikeTagsAndFieldsPagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit);
	
	@Query("SELECT distinct t from ThesisEntity t where t.name like ?1 and t.course.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?6  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?5)"
			+ ")")
	Page<Thesis> findByNameLikeTagsFieldsAndCoursePagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long matchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit);
	
	@Query("SELECT distinct t from ThesisEntity t join t.course.studies s where t.name like ?1 and s.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?6  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?5)"
			+ ")")
	Page<Thesis> findByNameLikeTagsFieldsAndStudiesPagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long matchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit);
	
	
	@Query("SELECT distinct t from ThesisEntity t where t.name like ?1 and ?3  <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?5  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?4)"
			+ ") and ?7  <= ("
			+ "SELECT count(tk.id) from ThesisEntity t4 left join t4.thesisKeywords tk where t4.id=t.id and tk.keyword.value in(?6)"
			+ ")")
	Page<Thesis> findByNameLikeTagsFieldsAndDescriptionPagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long tagsMatchLimit, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);
	
	@Query("SELECT distinct t from ThesisEntity t where t.name like ?1 and t.course.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?6  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?5)"
			+ ") and ?8  <= ("
			+ "SELECT count(tk.id) from ThesisEntity t4 left join t4.thesisKeywords tk where t4.id=t.id and tk.keyword.value in(?7)"
			+ ")")
	Page<Thesis> findByNameLikeTagsFieldsCourseAndDescriptioinPagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long matchLimit, String courseName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);
	
	@Query("SELECT distinct t from ThesisEntity t join t.course.studies s where t.name like ?1 and s.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from ThesisEntity t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ") and ?6  <= ("
			+ "SELECT count(fos.id) from ThesisEntity t3 left join t3.fieldOfStudies fos where t3.id=t.id and fos.name in(?5)"
			+ ") and ?8  <= ("
			+ "SELECT count(tk.id) from ThesisEntity t4 left join t4.thesisKeywords tk where t4.id=t.id and tk.keyword.value in(?7)"
			+ ")")
	Page<Thesis> findByNameLikeTagsFieldsStudiesAndDescriptionPagable(Pageable pageRequest, String thesisName, List<String> tagValues, Long matchLimit, String studiesName, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);
	
	
	
	
	/*@Query("SELECT distinct t from Thesis t where t.name like ?1 and ?3  <= ("
			+ "SELECT count(tag2.id) from Thesis t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ")")
	public List<Thesis> findByNameLikeAndTags(String thesisName,List<String> tagValues,Long matchLimit);
	
	@Query("SELECT distinct t from Thesis t where t.name like ?1 and t.subject.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from Thesis t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ")")
	public List<Thesis> findByNameLikeTagsAndSubject(String thesisName,List<String> tagValues,Long matchLimit,String subjectName);
	
	@Query("SELECT distinct t from Thesis t join t.subject.courses c where t.name like ?1 and c.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from Thesis t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ")")
	public List<Thesis> findByNameLikeTagsAndCourse(String thesisName,List<String> tagValues,Long matchLimit,String courseName);
		
	@Query("SELECT distinct t from Thesis t join t.subject.courses c join c.studies s where t.name like ?1 and s.name = ?4 and ?3 <= ("
			+ "SELECT count(tag2.id) from Thesis t2 left join t2.tags tag2 where t2.id=t.id and tag2.value in(?2)"
			+ ")")
	public List<Thesis> findByNameLikeTagsAndStudies(String thesisName,List<String> tagValues,Long matchLimit,String studiesName);
	*/
	
	
}
