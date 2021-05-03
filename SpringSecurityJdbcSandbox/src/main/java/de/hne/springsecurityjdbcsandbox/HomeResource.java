package de.hne.springsecurityjdbcsandbox;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines resource to be secured.
 */
@RestController
public class HomeResource {

	@GetMapping("/")
	public String home() {
		return("<h1>Aah, another visitor... stay a while.</h1>");
	}
	
	@GetMapping("/user")
	public String user() {
		return("<h1>Welcome user!</h1>");	
	}
	
	@GetMapping("/admin")
	public String admin() {
		return("<h1>Welcome admin!</h1>");	
	}
		
	
}
