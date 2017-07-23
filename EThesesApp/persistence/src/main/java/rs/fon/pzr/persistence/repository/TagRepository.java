package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
	
	Tag findByValue(String value);
	Set<Tag> findAll();

}
