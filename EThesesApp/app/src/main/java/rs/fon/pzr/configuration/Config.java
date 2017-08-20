package rs.fon.pzr.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@EnableDiscoveryClient
public class Config {
}
