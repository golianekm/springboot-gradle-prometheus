package golianekm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer
import io.opentracing.contrib.metrics.MetricsReporter
import io.opentracing.contrib.metrics.micrometer.MicrometerMetricsReporter
import io.opentracing.tag.Tags


@SpringBootApplication
class SpringbootPrometheusApplication {

	static void main(String[] args) {
		SpringApplication.run SpringbootPrometheusApplication, args
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}


	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return { MeterRegistry registry ->
			registry.config().commonTags("aplikacja", "spring-boot")
		}
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

	@Autowired
	MeterRegistry registry

	@Bean
	Set<MetricsReporter> metricsReporters(){
		
		MicrometerMetricsReporter reporter = MicrometerMetricsReporter.newMetricsReporter()
				.withName("tracing")
				.withConstLabel("span.kind", Tags.SPAN_KIND_CLIENT)
				.withRegistry(registry)
				//.withPercentiles(0.99)
				.build()
		Set<MetricsReporter> set = new HashSet<MetricsReporter>()
		set.add( reporter )
		return set
	}

	/*
	 @Autowired
	 Tracer tracer
	 @Bean
	 public io.opentracing.Tracer jaegerTracer() {
	 //io.micrometer.core.instrument.Metrics.addRegistry(new SimpleMeterRegistry());
	 //Tracer tracer = new Configuration("spring-boot", new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
	 //	new Configuration.ReporterConfiguration(true,"192.168.1.200",6831,1000,1 ) )
	 //	.getTracer();
	 MicrometerMetricsReporter reporter = MicrometerMetricsReporter.newMetricsReporter()
	 .withName("tracing")
	 .withConstLabel("span.kind", Tags.SPAN_KIND_CLIENT)
	 .withRegistry(registry)
	 //.withPercentiles(0.99)
	 .build();
	 Tracer metricsTracer = io.opentracing.contrib.metrics.Metrics.decorate(tracer, reporter);
	 return metricsTracer
	 }
	 */
}