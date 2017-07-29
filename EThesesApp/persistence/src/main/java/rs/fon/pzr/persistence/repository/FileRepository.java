package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.TFile;

public interface FileRepository extends CrudRepository<TFile, Long>{
	
	TFile findByFileName(String fileName);
	Set<TFile> findAll();
}
