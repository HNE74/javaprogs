package de.hne.securitysandbox;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines resource to be secured.
 */
@RestController
public class HomeResource {

	@GetMapping("/")
	public String home() {
		return("<h1>Welcome home!</h1>");
	}
	
}
