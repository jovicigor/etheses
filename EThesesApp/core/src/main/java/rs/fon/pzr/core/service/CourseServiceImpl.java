package rs.fon.pzr.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.CourseEntity;
import rs.fon.pzr.persistence.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseEntity getCourse(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public CourseEntity getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    @Override
    public CourseEntity addCourse(CourseEntity course) {
        if (courseRepository.findByName(course.getName()) != null) {
            throw new InvalidArgumentException("Kurs " + course.getName()
                    + " već postoji u bazi!");
        }
        if (courseRepository.findByNameShort(course.getNameShort()) != null) {
            throw new InvalidArgumentException("Kurs " + course.getNameShort()
                    + " već postoji u bazi!");
        }
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public CourseEntity updateCourse(CourseEntity course) {
        CourseEntity existingCourse = courseRepository.findOne(course.getId());
        if (existingCourse == null) {
            throw new InvalidArgumentException("Smer sa id-em "
                    + course.getId() + " ne postoji u bazi!");
        }

        if (!existingCourse.getName().equals(course.getName())) {
            if (courseRepository.findByName(course.getName()) != null) {
                throw new InvalidArgumentException("Kurs " + course.getName()
                        + " već postoji u bazi!");
            }
        }

        if (!existingCourse.getNameShort().equals(course.getNameShort())) {
            if (courseRepository.findByNameShort(course.getNameShort()) != null) {
                throw new InvalidArgumentException("Kurs "
                        + course.getNameShort() + " već postoji u bazi!");
            }
        }
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public void removeCourse(Long courseID) {
        CourseEntity course = courseRepository.findOne(courseID);
        if (course == null) {
            throw new InvalidArgumentException("Smer sa id-em " + courseID
                    + " ne postoji u bazi!");
        }
        courseRepository.delete(course);
    }
}
