package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.CreateInstructorRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.service.AuthenticathionService;
import ar.edu.unnoba.pm2026.courses.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

    private final InstructorService instructorService;
    private final ModelMapper modelMapper;
    private final AuthenticathionService authenticathionService;

    public InstructorResource(InstructorService instructorService, ModelMapper modelMapper, AuthenticathionService authenticathionService) {
        this.instructorService = instructorService;
        this.modelMapper = modelMapper;
        this.authenticathionService = authenticathionService;
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody InstructorAuthenticationRequestDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(dto.email());
        instructor.setContrasenia(dto.password());

        try {
            String token = authenticathionService.authenticate(instructor);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateInstructorRequestDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(dto.email());
        instructor.setContrasenia(dto.password());

        try {
            instructorService.create(instructor);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}