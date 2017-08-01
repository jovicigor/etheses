package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.TFile;

import java.util.Set;

public interface FileRepository {

    TFile findByFileName(String originalFilename);

    void save(TFile tFile);

    Set<TFile> findAll();


    TFile findOne(Long fileId);

    void delete(TFile existingFile);
}
