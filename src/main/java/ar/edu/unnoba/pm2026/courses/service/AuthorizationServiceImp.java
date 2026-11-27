package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImp implements AuthorizationService{
    private final JwtTokenUtil tokenUtil;
    private final InstructorService instructorService;

    public AuthorizationServiceImp(JwtTokenUtil tokenUtil, InstructorService instructorService) {
        this.tokenUtil = tokenUtil;
        this.instructorService = instructorService;
    }


    public Instructor authorize(String token) throws Exception{
        if(!tokenUtil.verify(token)){
            throw new Exception("Falló la Verificacion.");
        }
        String subject = tokenUtil.getSubject(token);
        Instructor instructor = instructorService.findByEmail(subject);
        if(instructor ==null){
            throw new Exception("No se encontro el email");
        }
        return instructor;
    }

}