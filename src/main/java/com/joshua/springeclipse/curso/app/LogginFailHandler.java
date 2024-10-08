package com.joshua.springeclipse.curso.app;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        FlashMap flashMap = new FlashMap();
        flashMap.put("error", "Usuario o contraseña incorrecto");
        SessionFlashMapManager flashManager = new SessionFlashMapManager();
        flashManager.saveOutputFlashMap(flashMap, request, response);
        response.sendRedirect("/loggin");
    
        for(int i=0; i<=100; i++) {
        System.out.print("Si esta tomando elfail");
        
        }}
}
	

