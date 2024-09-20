package co.edu.uniandes.dse.parcialprueba.repositories;

import org.springframework.stereotype.Repository;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long>
{    
}
