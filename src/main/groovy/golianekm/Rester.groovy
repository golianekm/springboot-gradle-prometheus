package golianekm

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@Timed("people")
class Rester {

	public Rester() {
		// TODO Auto-generated constructor stub
	}

	
	@GetMapping("/people")
	//@Timed(value = "people.all", longTask = false)
	public List<String> listPeople() {
		return ['ala','kot']
	}

	
	@Value('${HOSTNAME:N/A}')
	private String hostname;

	@Value('${SPRING_BOOT_PORT:N/A}')
	private String service;

	@GetMapping("/hostname")
	//@Timed(value = "people.all", longTask = false)
	public String hostname() {
		return "<html><body><h1>Hostname: ${hostname}</h1><h2>Service: ${service}</h2></body></html>"
	}

	
}
