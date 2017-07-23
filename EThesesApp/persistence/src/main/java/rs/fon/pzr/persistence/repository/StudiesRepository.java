package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.Studies;

public interface StudiesRepository extends CrudRepository<Studies, Long> {
	
	List<Studies> findAll();
	Studies findByName(String name);
	Studies findByNameShort(String nameShort);
}
