package josehomenhuck.mongo_project.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import josehomenhuck.mongo_project.entity.Student;
import josehomenhuck.mongo_project.entity.Address;
import josehomenhuck.mongo_project.entity.Gender;
import josehomenhuck.mongo_project.repository.StudentRepository;

@Service
@Transactional
public class StudentService {
  
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Student> findAllPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Student findStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email)
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(String id) {
        return studentRepository.findById(id);
    }

    public Student insert(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalStateException("Email already taken");
        }
        student.setActive(true);
        return studentRepository.insert(student);
    }

    public Student update(String email, Student student) {
        return studentRepository.findStudentByEmail(email)
            .map(existingStudent -> {
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setGender(student.getGender());
                
                if (student.getAddress() != null) {
                    existingStudent.setAddress(student.getAddress());
                }
                
                if (student.getFavoriteSubjects() != null) {
                    existingStudent.setFavoriteSubjects(student.getFavoriteSubjects());
                }
                
                if (student.getTotalSpentInBooks() != null) {
                    existingStudent.setTotalSpentInBooks(student.getTotalSpentInBooks());
                }
                
                return studentRepository.save(existingStudent);
            })
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    public void delete(String id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }

    public void softDelete(String id) {
        studentRepository.findById(id)
            .map(student -> {
                student.setActive(false);
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalStateException("Student with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Student> findByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    public Student updateAddress(String email, Address address) {
        return studentRepository.findStudentByEmail(email)
            .map(student -> {
                student.setAddress(address);
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    public Student addFavoriteSubject(String email, String subject) {
        return studentRepository.findStudentByEmail(email)
            .map(student -> {
                student.addFavoriteSubject(subject);
                
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    public Student removeFavoriteSubject(String email, String subject) {
        return studentRepository.findStudentByEmail(email)
            .map(student -> {
                student.removeFavoriteSubject(subject);
                
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    public Student addToBookSpending(String email, BigDecimal amount) {
        return studentRepository.findStudentByEmail(email)
            .map(student -> {
                student.addToTotalSpent(amount);
                
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Student> findByGender(Gender gender) {
        return studentRepository.findByGender(gender);
    }

    @Transactional(readOnly = true)
    public List<Student> findByActiveStatus(boolean active) {
        return studentRepository.findByActive(active);
    }

    @Transactional(readOnly = true)
    public List<Student> findByCity(String city) {
        return studentRepository.findByAddress_City(city);
    }

    @Transactional(readOnly = true)
    public List<Student> findByCountry(String country) {
        return studentRepository.findByAddress_Country(country);
    }

    @Transactional(readOnly = true)
    public List<Student> findByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }
}