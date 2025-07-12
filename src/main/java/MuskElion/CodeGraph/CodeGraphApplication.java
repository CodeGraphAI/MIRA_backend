package MuskElion.CodeGraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication
public class CodeGraphApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGraphApplication.class, args);
	}

}
