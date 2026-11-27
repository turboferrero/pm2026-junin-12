package ar.edu.unnoba.pm2026.courses.service;


import ar.edu.unnoba.pm2026.courses.dto.AdminResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Administrator;

import java.util.List;

public interface AdministratorService {
    public void create(Administrator administrator) throws Exception;
    public Administrator findByEmail(String email);
    public List<AdminResponseDTO> getAll();
    public void delete(Long id, Long authenticatedAdminId) throws Exception;
}
