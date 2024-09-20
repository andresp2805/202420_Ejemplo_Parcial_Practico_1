package co.edu.uniandes.dse.parcialprueba.repositories;

import org.springframework.stereotype.Repository;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long>
{    
}
