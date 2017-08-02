package rs.fon.pzr.core.repository;

import rs.fon.pzr.core.domain.model.studies.Studies;

import java.util.List;

public interface StudiesRepository {
    Studies findOne(Long id);

    Studies findByName(String name);

    List<Studies> findAll();

    Studies findByNameShort(String nameShort);

    Studies save(Studies studies);

    void delete(Studies studies);
}
