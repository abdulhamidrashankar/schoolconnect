package com.schoolconnect.app.controller.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.controller.LoginControllerAPI;
import com.schoolconnect.app.dto.login.LoginRequest;
import com.schoolconnect.app.dto.login.LoginResponse;
import com.schoolconnect.app.service.impl.LoginService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@Tag(name = "Login", description = "Authentication API") // Required again for Swagger to recognize
public class LoginControllerImpl implements LoginControllerAPI {

	private final LoginService loginService;

	@Autowired
	private AuthenticationManager authenticationManager;

	// Constructor injection enforces setting the final field
	public LoginControllerImpl(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);

			HttpSession session = httpRequest.getSession(true);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

			return ResponseEntity.ok(new LoginResponse("Login successful", session.getId()));

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new LoginResponse("Invalid username or password", null));
		}
	}

	@GetMapping("/session-info")
	public ResponseEntity<?> getSessionFromContext(HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| "anonymousUser".equals(authentication.getPrincipal())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User is not authenticated"));
		}

		return ResponseEntity.ok(Map.of("sessionId", session.getId(), "username", authentication.getName()));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidate the HTTP session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Clear the security context
		SecurityContextHolder.clearContext();

		// Optionally, clear cookies here if you want to (client usually handles this)
		// For example, you can add Set-Cookie headers to expire cookies

		return ResponseEntity.ok().body("{\"message\": \"Logout successful\"}");
	}
}
