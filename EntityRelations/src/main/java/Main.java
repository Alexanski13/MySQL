import Entities.Bike;
import Entities.Car;
import Entities.Plane;
import Entities.Vehicle;
import hasEntities.Article;
import hasEntities.PlateNumber;
import hasEntities.Truck;
import hasEntities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Vehicle car = new Car("Nissan GT-r", "Petrol", 4);
        Vehicle bike = new Bike();
        Vehicle plane = new Plane("Airbus", "JetFuel", 444);

        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);

        PlateNumber number = new PlateNumber("4444");
        Truck truck1 = new Truck(number);
        Truck truck2 = new Truck(number);

        entityManager.persist(number);
        entityManager.persist(truck1);
        entityManager.persist(truck2);

        Article article  = new Article("New GT-r");
        User author = new User("Yakuza");

        author.addArticle(article);

        entityManager.persist(author);

        entityManager.getTransaction().commit();


        entityManager.close();
    }
}
