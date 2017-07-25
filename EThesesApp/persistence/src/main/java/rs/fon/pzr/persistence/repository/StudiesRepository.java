package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.StudiesEntity;

public interface StudiesRepository extends CrudRepository<StudiesEntity, Long> {
	
	List<StudiesEntity> findAll();
	StudiesEntity findByName(String name);
	StudiesEntity findByNameShort(String nameShort);
}
