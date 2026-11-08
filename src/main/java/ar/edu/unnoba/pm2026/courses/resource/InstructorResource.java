package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.CreateInstructorRequestDTO;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

    private final InstructorService instructorService;
    private final ModelMapper modelMapper;

    public InstructorResource(InstructorService instructorService, ModelMapper modelMapper) {
        this.instructorService = instructorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateInstructorRequestDTO dto) {
        Instructor instructor = modelMapper.map(dto, Instructor.class);
        instructor.setContrasenia(dto.password());
        try {
            instructorService.create(instructor);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}