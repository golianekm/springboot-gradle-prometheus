package golianekm

import java.util.List

import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import io.micrometer.core.annotation.Timed

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

}
