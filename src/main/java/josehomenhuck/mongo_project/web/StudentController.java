package josehomenhuck.mongo_project.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import josehomenhuck.mongo_project.entity.Address;
import josehomenhuck.mongo_project.entity.Gender;
import josehomenhuck.mongo_project.entity.Student;
import josehomenhuck.mongo_project.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toLowerCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return ResponseEntity.ok(studentService.findAllPaginated(pageable));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> findStudentByEmail(@PathVariable String email) {
        try {
            Student student = studentService.findStudentByEmail(email);
            return ResponseEntity.ok(student);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Student> findById(@PathVariable String id) {
        return studentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> insert(@Validated @RequestBody Student student) {
        try {
            Student savedStudent = studentService.insert(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<Student> update(@PathVariable String email, @Validated @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.update(email, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("soft/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable String id) {
        try {
            studentService.softDelete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Student>> findByLastName(@PathVariable String lastName) {
        List<Student> students = studentService.findByLastName(lastName);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<Student>> findByFirstName(@PathVariable String firstName) {
        List<Student> students = studentService.findByFirstName(firstName);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.existsByEmail(email));
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<List<Student>> findByActive(@PathVariable boolean active) {
        List<Student> students = studentService.findByActiveStatus(active);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @GetMapping("gender/{gender}")
    public ResponseEntity<List<Student>> findByGender(@PathVariable Gender gender) {
        List<Student> students = studentService.findByGender(gender);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @GetMapping("city/{city}")
    public ResponseEntity<List<Student>> findByCity(@PathVariable String city) {
        List<Student> students = studentService.findByCity(city);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @GetMapping("country/{country}")
    public ResponseEntity<List<Student>> findByCountry(@PathVariable String country) {
        List<Student> students = studentService.findByCountry(country);
        return students.isEmpty() 
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(students);
    }

    @PutMapping("address/{email}")
    public ResponseEntity<Student> updateAddress(@PathVariable String email, @RequestBody Address address) {
        try {
            Student updatedStudent = studentService.updateAddress(email, address);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("favorite/{email}/{subject}")
    public ResponseEntity<Student> addFavoriteSubject(@PathVariable String email, @PathVariable String subject) {
        try {
            Student updatedStudent = studentService.addFavoriteSubject(email, subject);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("favorite/{email}/{subject}")
    public ResponseEntity<Student> removeFavoriteSubject(@PathVariable String email, @PathVariable String subject) {
        try {
            Student updatedStudent = studentService.removeFavoriteSubject(email, subject);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("spending/{email}/{amount}")
    public ResponseEntity<Student> addToBookSpending(@PathVariable String email, @PathVariable BigDecimal amount) {
        try {
            Student updatedStudent = studentService.addToBookSpending(email, amount);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }
}