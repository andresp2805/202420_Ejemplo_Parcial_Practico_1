package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MedicoEspecialidadService 
{
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException 
    {
        log.info("Inicia proceso de asociarle una Especialidad al Médico con id = {}", medicoId);
        Optional<MedicoEntity> medicoEntityOptional = medicoRepository.findById(medicoId);
        if (medicoEntityOptional.isEmpty()) 
        {
            throw new EntityNotFoundException("Médico no encontrado");
        }
        Optional<EspecialidadEntity> especialidadEntityOptional = especialidadRepository.findById(especialidadId);
        if (especialidadEntityOptional.isEmpty()) 
        {
            throw new EntityNotFoundException("Especialidad no encontrada");
        }
        MedicoEntity medicoEntity = medicoEntityOptional.get();
        EspecialidadEntity especialidadEntity = especialidadEntityOptional.get();

        medicoEntity.getEspecialidades().add(especialidadEntity);
        especialidadEntity.getMedicos().add(medicoEntity);

        medicoRepository.save(medicoEntity);
        especialidadRepository.save(especialidadEntity);

        log.info("Termina proceso de asociarle una Especialidad al Médico con id = {}", medicoId);
        return especialidadEntity;
    }
}

