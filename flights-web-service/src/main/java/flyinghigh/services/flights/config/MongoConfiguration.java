package flyinghigh.services.flights.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

import static org.springframework.util.StringUtils.isEmpty;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration {

    private @Autowired Environment environment;

    public @Bean MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient();
    }

    public @Bean MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        return mongo;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        MongoClient client = new MongoClient( new MongoClientURI(getMongoURI()));
        return new SimpleMongoDbFactory(client,database());
    }

    public String getMongoURI() {
        String username = environment.getProperty("mongodb.username");
        String password = environment.getProperty("mongodb.password");
        String host = environment.getProperty("mongodb.host");
        String port = environment.getProperty("mongodb.port");
        String credentials = (isEmpty(username)) ? "" : username + ":" + password + "@";

        String uri = "mongodb://" + credentials +  host + ":" + port + "/" + database();
        System.out.println("CONNECTING TO MONGODB on " + uri);
        return uri;
    }

    public String database() {
        return environment.getProperty("mongodb.database","flyinghigh");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }
}
