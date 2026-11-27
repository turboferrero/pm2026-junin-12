package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.model.Instructor;

public interface AuthenticathionService {
    public String authenticate(Instructor instructor) throws Exception;
}
