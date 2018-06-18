package golianekm

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration
import org.springframework.context.annotation.Bean

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer

@SpringBootApplication
//@EnablePrometheusEndpoint
//@EnableSpringBootMetricsCollector

class SpringbootPrometheusApplication {

	static void main(String[] args) {
		SpringApplication.run SpringbootPrometheusApplication, args
	}
	
	
	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
	  return { registry -> registry.config().commonTags("aplikacja", "spring-boot") }
	}
	
	@Bean
	JvmThreadMetrics threadMetrics(){
		return new JvmThreadMetrics();
	}
	
	@Bean
	FileDescriptorMetrics filesMetrics() {
		return new FileDescriptorMetrics();
	}
	
	@Bean
	UptimeMetrics uptimeMetrics() {
		return new UptimeMetrics();
	}
	
	
	
}
