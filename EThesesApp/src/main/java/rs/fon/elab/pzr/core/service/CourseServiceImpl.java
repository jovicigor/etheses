package rs.fon.elab.pzr.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.elab.pzr.core.exception.InvalidArgumentException;
import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    @Override
    public Course addCourse(Course course) {
        if (courseRepository.findByName(course.getName()) != null) {
            throw new InvalidArgumentException("Kurs " + course.getName()
                    + " već postoji u bazi!");
        }
        ;
        if (courseRepository.findByNameShort(course.getNameShort()) != null) {
            throw new InvalidArgumentException("Kurs " + course.getNameShort()
                    + " već postoji u bazi!");
        }
        ;
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

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


}
