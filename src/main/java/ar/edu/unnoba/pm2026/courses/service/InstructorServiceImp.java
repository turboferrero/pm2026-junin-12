package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.repository.InstructorRepository;
import ar.edu.unnoba.pm2026.courses.util.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImp implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    public InstructorServiceImp(InstructorRepository instructorRepository, PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void create(Instructor instructor) throws Exception {
        Instructor existente=instructorRepository.findByEmail(instructor.getEmail());
        if(existente!=null){
            throw new Exception("El email ya existe en el sistema");
        }
        instructor.setContrasenia(passwordEncoder.encode(instructor.getContrasenia()));
        instructorRepository.save(instructor);
    }
}
