package rs.fon.elab.pzr.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.elab.pzr.core.model.Studies;

public interface StudiesRepository extends CrudRepository<Studies, Long> {
	
	List<Studies> findAll();
	Studies findByName(String name);
	Studies findByNameShort(String nameShort);
}
