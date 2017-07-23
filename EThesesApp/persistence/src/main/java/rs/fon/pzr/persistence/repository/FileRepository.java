package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.TFileEntity;

public interface FileRepository extends CrudRepository<TFileEntity, Long>{
	
	TFileEntity findByFileName(String fileName);
	Set<TFileEntity> findAll();
}
