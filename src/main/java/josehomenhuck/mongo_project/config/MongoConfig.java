package josehomenhuck.mongo_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
  // Only for createdAt and UpdatedAt
}