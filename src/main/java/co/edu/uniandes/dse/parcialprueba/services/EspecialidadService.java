package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService 
{
    @Autowired
    EspecialidadRepository especialidadRepository;
  
    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws EntityNotFoundException, IllegalOperationException 
    {
        log.info("Creando Especialidad");
        if (especialidadEntity.getDescripcion().length() < 10) 
        {
            throw new IllegalOperationException("Información de la Especialidad inválida.");   
        }
        log.info("Especialidad creada");
        return especialidadRepository.save(especialidadEntity);
    }
}
