package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.util.JwtTokenUtil;
import ar.edu.unnoba.pm2026.courses.util.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticathionService {
    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    public AuthenticationServiceImp(InstructorService instructorService, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public String authenticate(Instructor instructor) throws Exception {
      Instructor instructor1=instructorService.findByEmail(instructor.getEmail());
      if(instructor1==null){
          throw new Exception("Instructor not found");
      }
      boolean contraseñaVerificada = passwordEncoder.verify(instructor.getContrasenia(),instructor1.getContrasenia());
      if(!contraseñaVerificada){
          throw new Exception("La contraseña no es la misma");
      }
      String tokenGenerado=tokenUtil.generateToken(instructor1.getEmail());
      return tokenGenerado;
    }
}
