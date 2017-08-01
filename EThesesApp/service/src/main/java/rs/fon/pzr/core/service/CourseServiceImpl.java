package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.repository.CourseRepository;
import rs.fon.pzr.model.studies.Course;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Course> getCourse(Long id) {
        Course course = courseRepository.findOne(id);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> getCourseByName(String name) {
        Course course = courseRepository.findByName(name);
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    @Override
    public Course addCourse(Course course) {
        if (courseRepository.existsByName(course.getName())) {
            throw new InvalidArgumentException("Kurs " + course.getName()
                    + " već postoji u bazi!");
        }
        if (courseRepository.existsByNameShort(course.getNameShort())) {
            throw new InvalidArgumentException("Kurs " + course.getNameShort()
                    + " već postoji u bazi!");
        }
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public Course updateCourse(Course course) {
        Course existingCourse = courseRepository.findOne(course.getId());
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
        Course course = courseRepository.findOne(courseID);
        if (course == null) {
            throw new InvalidArgumentException("Smer sa id-em " + courseID
                    + " ne postoji u bazi!");
        }
        courseRepository.delete(course);
    }
}
