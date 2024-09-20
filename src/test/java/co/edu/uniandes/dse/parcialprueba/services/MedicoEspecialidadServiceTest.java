package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({MedicoEspecialidadService.class})
public class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;
    
    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<>();
    private List<EspecialidadEntity> especialidadList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        // Insertar datos de Médicos
        for (int i = 0; i < 3; i++) {
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(medicoEntity);
            medicoList.add(medicoEntity);
        }

        // Insertar datos de Especialidades
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(especialidadEntity);
            especialidadList.add(especialidadEntity);
        }
    }

    /**
     * Prueba para agregar correctamente una especialidad a un médico.
     */
    @Test
    void testAddEspecialidadSuccess() throws EntityNotFoundException {
        MedicoEntity medico = medicoList.get(0);
        EspecialidadEntity especialidad = especialidadList.get(0);

        // Act
        EspecialidadEntity result = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());

        // Assert
        assertNotNull(result);
        assertNotNull(entityManager.find(MedicoEntity.class, medico.getId()).getEspecialidades().contains(especialidad));
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando el médico no existe.
     */
    @Test
    void testAddEspecialidadMedicoNotFound() {
        EspecialidadEntity especialidad = especialidadList.get(0);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(0L, especialidad.getId()); // ID inválido de médico
        });
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando la especialidad no existe.
     */
    @Test
    void testAddEspecialidadEspecialidadNotFound() {
        MedicoEntity medico = medicoList.get(0);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(medico.getId(), 0L); // ID inválido de especialidad
        });
    }
}
