package workshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.models.companies.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
