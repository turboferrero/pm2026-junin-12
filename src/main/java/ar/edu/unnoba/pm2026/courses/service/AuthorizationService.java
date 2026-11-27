package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.model.Administrator;
import ar.edu.unnoba.pm2026.courses.model.Instructor;

public interface AuthorizationService {

    public Instructor authorize(String token) throws Exception;
    public Administrator authorizeAdmin(String token) throws Exception;

}