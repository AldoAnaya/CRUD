package com.ProyectoCiclo3.CRUD.Controller;

import com.ProyectoCiclo3.CRUD.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    ProfileService profileService;

    @GetMapping("/")
    private String login(Model model, @AuthenticationPrincipal OidcUser principal){ // eso que recibe el controlador es basicamente el objeto que va a tener los datos de autenticacion que meta en auth0
       if(principal!=null){
           Boolean isVerificado = profileService.verificarUsuario(principal.getClaims());
           System.out.println(isVerificado);
           model.addAttribute("isVerificado",isVerificado);
       }
        return "login";
    }

    @GetMapping("/index")
    private String index(){
        return "index";
    }
}
