package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.studies.StudiesEntity;
import rs.fon.pzr.persistence.repository.StudiesRepository;

@Service
public class StudiesServiceImpl implements StudiesService {

    private final StudiesRepository studiesRepository;

    @Autowired
    public StudiesServiceImpl(StudiesRepository studiesRepository) {
        this.studiesRepository = studiesRepository;
    }

    @Override
    public Optional<StudiesEntity> getStudies(Long id) {
        StudiesEntity studies = studiesRepository.findOne(id);

        return Optional.ofNullable(studies);
    }

    @Override
    public Optional<StudiesEntity> getStudiesByName(String name) {
        StudiesEntity studies = studiesRepository.findByName(name);
        return Optional.ofNullable(studies);
    }

    @Override
    public List<StudiesEntity> getAllStudies() {
        return studiesRepository.findAll();
    }


    @Transactional
    @Override
    public StudiesEntity addStudies(StudiesEntity studies) {
        if (studiesRepository.findByName(studies.getName()) != null) {
            throw new InvalidArgumentException("Nivo studija " + studies.getName()
                    + " već postoji u bazi!");
        }
        if (studiesRepository.findByNameShort(studies.getNameShort()) != null) {
            throw new InvalidArgumentException("Nivo studija " + studies.getNameShort()
                    + " već postoji u bazi!");
        }
        return studiesRepository.save(studies);
    }


    @Transactional
    @Override
    public StudiesEntity updateStudies(StudiesEntity studies) {
        StudiesEntity existingStudies = studiesRepository.findOne(studies.getId());
        if (existingStudies == null) {
            throw new InvalidArgumentException("Nivo studija sa id-em "
                    + studies.getId() + " ne postoji u bazi!");
        }

        if (!existingStudies.getName().equals(studies.getName())) {
            if (studiesRepository.findByName(studies.getName()) != null) {
                throw new InvalidArgumentException("Nivo studija " + studies.getName()
                        + " već postoji u bazi!");
            }
        }

        if (!existingStudies.getNameShort().equals(studies.getNameShort())) {
            if (studiesRepository.findByNameShort(studies.getNameShort()) != null) {
                throw new InvalidArgumentException("Nivo studija "
                        + studies.getNameShort() + " već postoji u bazi!");
            }
        }
        return studiesRepository.save(studies);
    }

    @Transactional
    @Override
    public void removeStudies(Long studiesID) {
        StudiesEntity studies = studiesRepository.findOne(studiesID);
        if (studies == null) {
            throw new InvalidArgumentException("Nivo studija sa id-em " + studiesID
                    + " ne postoji u bazi!");
        }
        studiesRepository.delete(studies);
    }
}
