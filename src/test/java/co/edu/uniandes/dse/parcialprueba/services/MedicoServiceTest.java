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
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({MedicoService.class})
public class MedicoServiceTest 
{
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;
    
    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<MedicoEntity>();

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
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(medicoEntity);
            medicoList.add(medicoEntity);
        }
    }

    @Test
    void testCreateMedicos() throws EntityNotFoundException, IllegalOperationException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Andres");
        newEntity.setApellido("Pereira");
        newEntity.setRegistroMedico("RM1475");
        MedicoEntity result = medicoService.createMedico(newEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
    }

    @Test
    void testCreateMedicosWithNoValidRegistro() {
        assertThrows(IllegalOperationException.class, () -> {
            MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
            newEntity.setNombre("Andres");
            newEntity.setApellido("Pereira");
            newEntity.setRegistroMedico("AR1475");
            medicoService.createMedico(newEntity);
        });
    }
}
