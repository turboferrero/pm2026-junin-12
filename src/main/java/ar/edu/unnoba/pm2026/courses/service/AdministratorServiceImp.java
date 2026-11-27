package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.AdminResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Administrator;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.repository.AdministratorRepository;
import ar.edu.unnoba.pm2026.courses.util.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdministratorServiceImp implements AdministratorService{
    private AdministratorRepository administratorRepository;
    private PasswordEncoder passwordEncoder;

    public AdministratorServiceImp(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(Administrator administrator) throws Exception {
        Administrator existente=findByEmail(administrator.getEmail());
        if(existente!=null){
            throw new Exception("El email ya existe en el sistema");
        }
        administrator.setContrasenia(passwordEncoder.encode(administrator.getContrasenia()));
        administratorRepository.save(administrator);
    }

    @Override
    public Administrator findByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }

    @Override
    public List<AdminResponseDTO> getAll() {
        List<Administrator> administrators=administratorRepository.findAll();
        List<AdminResponseDTO> response=new ArrayList<>();
        for (Administrator admin : administrators) {
            AdminResponseDTO dto = new AdminResponseDTO(
                    admin.getId(),
                    admin.getNombre(),
                    admin.getApellido(),
                    admin.getEmail()
            );
            response.add(dto);
        }
        return  response;
    }

    @Override
    public void delete(Long id, Long authenticatedAdminId) throws Exception {

        if (id.equals(authenticatedAdminId)) {
            throw new IllegalStateException("Un administrador no puede eliminarse a sí mismo");
        }

        if (!administratorRepository.existsById(id)) {
            throw new NoSuchElementException("No existe un administrador con el ID proporcionado");
        }

        administratorRepository.deleteById(id);
    }

}
