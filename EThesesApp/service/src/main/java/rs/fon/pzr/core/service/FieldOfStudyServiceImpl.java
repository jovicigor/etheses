package rs.fon.pzr.core.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.FieldOfStudyEntity;
import rs.fon.pzr.persistence.repository.FieldOfStudyRepository;

@Service
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    public FieldOfStudyServiceImpl(FieldOfStudyRepository fieldOfStudyRepository) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
    }

    @Override
    public FieldOfStudyEntity getFieldOfStudy(Long id) {
        return fieldOfStudyRepository.findOne(id);
    }

    @Override
    public Set<FieldOfStudyEntity> getAllFieldsOfStudy() {
        return fieldOfStudyRepository.findAll();
    }

    @Transactional
    @Override
    public FieldOfStudyEntity addFieldOfStudy(String name) {
        FieldOfStudyEntity fieldOfStudy = fieldOfStudyRepository.findByName(name);
        if (fieldOfStudy != null) {
            return fieldOfStudy;
        }
        fieldOfStudy = new FieldOfStudyEntity(name);

        return fieldOfStudyRepository.save(fieldOfStudy);
    }

    @Override
    @Transactional
    public FieldOfStudyEntity updateFieldOfStudy(FieldOfStudyEntity fieldOfStudy) {
        FieldOfStudyEntity oldFieldOfStudy = fieldOfStudyRepository.findOne(fieldOfStudy.getId());
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
        FieldOfStudyEntity fieldOfStudy = fieldOfStudyRepository.findOne(id);
        if (fieldOfStudy == null) {
            throw new InvalidArgumentException("Oblast sa id-em " + id
                    + " ne postoji u bazi!");
        }
        fieldOfStudyRepository.delete(id);
    }
}
