package rs.fon.pzr.core.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.thesis.FieldOfStudy;
import rs.fon.pzr.persistence.repository.FieldOfStudyRepository;

@Service
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    public FieldOfStudyServiceImpl(FieldOfStudyRepository fieldOfStudyRepository) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
    }

    @Override
    public Optional<FieldOfStudy> getFieldOfStudy(Long id) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findOne(id);
        return Optional.ofNullable(fieldOfStudy);
    }

    @Override
    public Set<FieldOfStudy> getAllFieldsOfStudy() {
        return fieldOfStudyRepository.findAll();
    }

    @Transactional
    @Override
    public FieldOfStudy addFieldOfStudy(String name) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findByName(name);
        if (fieldOfStudy != null) {
            return fieldOfStudy;
        }
        fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);

        return fieldOfStudyRepository.save(fieldOfStudy);
    }

    @Override
    @Transactional
    public FieldOfStudy updateFieldOfStudy(FieldOfStudy fieldOfStudy) {
        FieldOfStudy oldFieldOfStudy = fieldOfStudyRepository.findOne(fieldOfStudy.getId());
        if (oldFieldOfStudy == null) {
            throw new InvalidArgumentException("Oblast sa id-em " + fieldOfStudy.getId()
                    + " ne postoji u bazi!");
        }
        if (!fieldOfStudy.getName().equals(oldFieldOfStudy.getName())) {
            if (fieldOfStudyRepository.findByName(fieldOfStudy.getName()) != null) {
                throw new InvalidArgumentException("Oblast " + fieldOfStudy.getName()
                        + "već postoji u bazi!");
            }
        }
        return fieldOfStudyRepository.save(fieldOfStudy);
    }

    @Transactional
    @Override
    public void removeFieldOfStudy(Long id) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findOne(id);
        if (fieldOfStudy == null) {
            throw new InvalidArgumentException("Oblast sa id-em " + id
                    + " ne postoji u bazi!");
        }
        fieldOfStudyRepository.delete(id);
    }
}
