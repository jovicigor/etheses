package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.service.repository.FileRepository;
import rs.fon.pzr.core.domain.model.thesis.TFile;
import rs.fon.pzr.persistence.jpa.FileJpaRepository;

import java.util.Set;

@Repository
public class FileRepositoryImpl implements FileRepository {
    
    private final FileJpaRepository jpaRepository;

    @Autowired
    public FileRepositoryImpl(FileJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TFile findByFileName(String originalFilename) {
        return jpaRepository.findByFileName(originalFilename);
    }

    @Override
    public void save(TFile tFile) {
        jpaRepository.save(tFile);
    }

    @Override
    public Set<TFile> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public TFile findOne(Long fileId) {
        return jpaRepository.findOne(fileId);
    }

    @Override
    public void delete(TFile existingFile) {
        jpaRepository.delete(existingFile);
    }
}
