package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

class NoEntityAnnotationTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
