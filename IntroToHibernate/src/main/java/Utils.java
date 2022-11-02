import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

class Utils {
    static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME)
                .createEntityManager();
    }
}
