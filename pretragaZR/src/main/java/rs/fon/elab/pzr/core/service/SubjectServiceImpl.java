package rs.fon.elab.pzr.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import rs.fon.elab.pzr.core.exception.InvalidArgumentException;
import rs.fon.elab.pzr.core.model.Subject;
import rs.fon.elab.pzr.core.repository.SubjectRepository;

public class SubjectServiceImpl implements SubjectService {

	private SubjectRepository subjectRepository;

	@Override
	public Subject getSubject(Long id) {
		return subjectRepository.findOne(id);
	}

	@Override
	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	@Override
	public Subject getSubjectByName(String name) {
		return subjectRepository.findByName(name);
	}

	@Transactional
	@Override
	public Subject addSubject(Subject subject) {
		if (subjectRepository.findByName(subject.getName()) != null) {
			throw new InvalidArgumentException("Predmet " + subject.getName()
					+ " već postoji u bazi!");
		}
		return subjectRepository.save(subject);
	}

	@Transactional
	@Override
	public Subject updateSubject(Subject subject) {
		Subject existingSubject = subjectRepository.findOne(subject.getId());
		if (existingSubject == null) {
			throw new InvalidArgumentException("Predmet sa id-em "
					+ subject.getId() + " ne postoji u bazi!");
		}
		if (!subject.getName().equals(existingSubject.getName())) {
			if (subjectRepository.findByName(subject.getName()) != null) {
				throw new InvalidArgumentException("Predmet "
						+ subject.getName() + " već postoji u bazi!");
			}
		}
		return subjectRepository.save(subject);
	}

	@Transactional
	@Override
	public void removeSubject(Long subjectID) {
		Subject subject = subjectRepository.findOne(subjectID);
		if (subject == null) {
			throw new InvalidArgumentException("Predmet sa id-em " + subjectID
					+ " ne postoji u bazi!");
		}
		subjectRepository.delete(subject);
	}

	// GETTERS AND SETTERS
	public SubjectRepository getSubjectRepository() {
		return subjectRepository;
	}

	public void setSubjectRepository(SubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}

}
