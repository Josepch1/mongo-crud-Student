package josehomenhuck.mongo_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import josehomenhuck.mongo_project.entity.Student;
import josehomenhuck.mongo_project.entity.Gender;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findStudentByEmail(String email);
    boolean existsByEmail(String email);
    List<Student> findByLastName(String lastName);
    List<Student> findByGender(Gender gender);
    List<Student> findByActive(boolean active);
    List<Student> findByAddress_City(String city);
    List<Student> findByAddress_Country(String country);
    List<Student> findByFirstName(String firstName);
}