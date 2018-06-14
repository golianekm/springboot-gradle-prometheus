package golianekm

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector

@SpringBootApplication
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
class SpringbootPrometheusApplication {

	static void main(String[] args) {
		SpringApplication.run SpringbootPrometheusApplication, args
	}
}
