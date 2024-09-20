package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({EspecialidadService.class})
class EspecialidadServiceTest 
{
    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EspecialidadEntity> especialidadList = new ArrayList<EspecialidadEntity>();

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
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(especialidadEntity);
            especialidadList.add(especialidadEntity);
        }
    }

    @Test
    void testCreateEspecialidad() throws EntityNotFoundException, IllegalOperationException {
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setNombre("Cardiologia");
        newEntity.setDescripcion("Especialidad que estudia el corazón");
        EspecialidadEntity result = especialidadService.createEspecialidad(newEntity);
        assertNotNull(result);
        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    @Test
    void testCreateEspecialidadDescripcion() {
        assertThrows(IllegalOperationException.class, () -> {
            EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            newEntity.setNombre("Andres");
            newEntity.setDescripcion("Corazón");
            especialidadService.createEspecialidad(newEntity);
        });
    }
}