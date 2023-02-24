package logiless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import logiless.config.serializer.NullValueSerializer;

@Configuration
public class JacksonConfig {
	/**
	 * jsondataを変換するときにnullにするやつだったかな TODO
	 * 
	 * @return
	 */
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapper() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			@Override
			public void configure(ObjectMapper objectMapper) {
				super.configure(objectMapper);
				DefaultSerializerProvider.Impl dsp = new DefaultSerializerProvider.Impl();
				dsp.setNullValueSerializer(new NullValueSerializer());
				objectMapper.setSerializerProvider(dsp);
			}
		};
		return builder;
	}
}
