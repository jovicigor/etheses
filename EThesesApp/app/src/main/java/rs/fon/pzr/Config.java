package rs.fon.pzr;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@EnableDiscoveryClient
public class Config {
}
