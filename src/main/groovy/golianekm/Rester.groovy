package golianekm

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@Timed("people")
class Rester {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Rester() {
		// TODO Auto-generated constructor stub
	}

	
	@Autowired
	private io.opentracing.Tracer tracer;
	
	@RequestMapping("/people")
	//@Timed(value = "people.all", longTask = false)
	public List<String> listPeople() {
		return ['ala','kot']
	}

	
	@Value('${HOSTNAME:N/A}')
	private String hostname;

	@Value('${SPRING_BOOT_PORT:N/A}')
	private String service;

	@RequestMapping("/hostname")
	//@Timed(value = "people.all", longTask = false)
	public String hostname() {
		log.info("Wywolano hostname")
		tracer.activeSpan().setBaggageItem("transaction", "hostname");
		//try {
		//	throw new RuntimeException("Byczek")
		//} catch ( e ) {
		//	log.error( "Byczek:", e )
		//}
		return "<html><body><h1>Hostname: ${hostname}</h1><h2>Service: ${service}</h2></body></html>"
	}

	
}
