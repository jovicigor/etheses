package rs.fon.pzr.persistence.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.TFile;

public interface FileJpaRepository extends CrudRepository<TFile, Long>{
	
	TFile findByFileName(String fileName);
	Set<TFile> findAll();
}
