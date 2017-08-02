package rs.fon.pzr.persistence.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.domain.model.studies.Studies;

public interface StudiesJpaRepository extends CrudRepository<Studies, Long> {
	
	List<Studies> findAll();
	Studies findByName(String name);
	Studies findByNameShort(String nameShort);
}
