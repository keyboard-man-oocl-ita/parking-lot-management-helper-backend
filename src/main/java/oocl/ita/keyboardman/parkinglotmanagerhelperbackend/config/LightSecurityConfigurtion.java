package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.config;

import com.itmuch.lightsecurity.enums.HttpMethod;
import com.itmuch.lightsecurity.spec.SpecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LightSecurityConfigurtion {
  @Bean
  public SpecRegistry specRegistry(){
    return new SpecRegistry()
        .add(HttpMethod.POST, "users/login", "anon()")
        .add(HttpMethod.POST, "clerks/login", "anon()")
        .add(HttpMethod.POST, "users/register", "anon()")
        .add(HttpMethod.POST, "clerks/register", "anon()");
  }
}
