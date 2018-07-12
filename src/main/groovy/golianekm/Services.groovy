package golianekm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

import groovy.util.logging.Slf4j

@RestController
@Slf4j
class Services {

	@Autowired
	private io.opentracing.Tracer tracer;
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value="/simpleList",produces=['application/json'],method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<String> simpleList() {
		log.debug("Wywolano simpleList")
		tracer.activeSpan().setBaggageItem("transaction", "simpleList");
		return ['ala', 'ma', 'kota']
	}

	@Value('${HOSTNAME:N/A}')
	private String hostname;

	@Value('${SPRING_BOOT_PORT:N/A}')
	private String service;
	

	@RequestMapping(value="/hostname",produces=['text/html'],method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String hostname() {
		log.debug("Wywolano hostname")
		//tracer.activeSpan().setBaggageItem("transaction", "hostname");
		return "<html><body><h1>Hostname: ${hostname}</h1><h2>Service: ${service}</h2></body></html>"
	}

	@RequestMapping(value="/throwError", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String throwError() {
		log.debug("Wywolano throwError")
		tracer.activeSpan().setBaggageItem("transaction", "throwError");
		tracer.activeSpan().setTag("Ala", "kot")
		throw new RuntimeException("Błąd wywołania usługi")
	}

	@RequestMapping(value="/badRequest", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String badRequest() {
		log.debug("Wywolano badRequest")
		tracer.activeSpan().setBaggageItem("transaction", "hostname");
	}
	
	@Value('${SPRING_BOOT_SERVICE:localhost:8080}')
	private String serviceName;
	
	@RequestMapping(value="/cascade", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List cascade() {
		log.debug("Wywolano cascade")
		tracer.activeSpan().setBaggageItem("transaction", "cascade");
		ResponseEntity<String> response = restTemplate.getForEntity("http://${serviceName}/simpleList", List.class);
		return response.getBody();
	}

	@RequestMapping(value="/multicascade", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List multCascade() {
		log.debug("Wywolano multicascade")
		tracer.activeSpan().setBaggageItem("transaction", "multicascade");
		ResponseEntity<String> response = restTemplate.getForEntity("http://${serviceName}/cascade", List.class);
		return response.getBody();
	}

}
