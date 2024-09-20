package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService 
{
    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws EntityNotFoundException, IllegalOperationException 
    {
        log.info("Creando Médico");
        if (!medicoEntity.getRegistroMedico().startsWith("RM")) 
        {
            throw new IllegalOperationException("Información del Médico inválida.");   
        }
        log.info("Médico creadO");
        return medicoRepository.save(medicoEntity);
    }
}
