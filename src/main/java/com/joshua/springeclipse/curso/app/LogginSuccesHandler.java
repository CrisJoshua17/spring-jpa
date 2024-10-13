package com.joshua.springeclipse.curso.app;

	import java.io.IOException;

	import org.springframework.security.core.Authentication;
	import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
	import org.springframework.stereotype.Component;
	import org.springframework.web.servlet.FlashMap;
	import org.springframework.web.servlet.support.SessionFlashMapManager;

	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;


	@Component
	public class LogginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {

			SessionFlashMapManager flashManager = new SessionFlashMapManager();
			FlashMap flashMap = new FlashMap();
			response.sendRedirect("/listar");
		
			flashMap.put("success", "Hola "+authentication.getName() +", haz iniciado sesion con exito ");
			flashManager.saveOutputFlashMap(flashMap, request, response);
			
			
			
		}
		
		

		
		
		
	}
