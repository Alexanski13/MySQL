import entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class GetEmployeeWithProject {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        final int employeeID = new Scanner(System.in).nextInt();

        System.out.println(entityManager.createQuery(Constants.Queries.GET_EMPLOYEE_BY_ID, Employee.class)
                .setParameter(Constants.PlaceHolders.ID, employeeID)
                .getSingleResult()
                .toString());

    }
}
