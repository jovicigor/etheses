package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
	
	TagEntity findByValue(String value);
	Set<TagEntity> findAll();

}
