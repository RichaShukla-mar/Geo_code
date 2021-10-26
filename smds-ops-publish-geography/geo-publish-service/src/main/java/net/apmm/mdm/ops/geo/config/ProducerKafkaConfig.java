package net.apmm.mdm.ops.geo.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
@Slf4j
public class ProducerKafkaConfig {

    private JdbcTemplate smdsMdJdbcTemplate;
    @Qualifier("empPublishGeoConfiguration")
    @Autowired
    String empPublishGeoConfiguration;

    @Value("${kafka.producer.linger:}")
    private  String producerLinger;
    @Value("${kafka.producer.timeout:}")
    private  String producerRequestTimeout;
    @Value("${kafka.producer.batch-size:}")
    private  String producerBatchSize;
    @Value("${kafka.producer.send-buffer:}")
    private  String producerSendBuffer;

    @Value("${kafka.producer.acks-config:}")
    private  String producerAcksConfig;
    @Value("${kafka.producer.client-id:}")
    private  String kafkaClientId;


    @Autowired
    ResourceLoader resourceLoader;
    /**
     * Setting properties for Kafka
     *
     * @return Map<String, Object>
     */

    @Autowired
    public ProducerKafkaConfig(@Qualifier("smdsMdJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsMdJdbcTemplate = jdbcTemplate;
    }


    public Properties retrieveEmpPublishCustomerConf() {
        log.debug("Fetching EMP Publish Geo configurations.. " );
        Properties properties = new Properties();

        try {
            smdsMdJdbcTemplate.query(empPublishGeoConfiguration, rs->{
                properties.put(rs.getString("PROPERTY_NAME"), rs.getString("PROPERTY_VALUE"));
            });

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting EMP Publish Geo configurations :: " + e);
        }
        return properties;
    }


    @Bean
    public ProducerFactory<String, String> producerConfigs() throws IOException {
        Map<String, Object> kafakaProperties = new HashMap<>();
        Properties configProperties = retrieveEmpPublishCustomerConf();

        kafakaProperties=onPremMixedProducerFactory(configProperties);

        return new DefaultKafkaProducerFactory<>(kafakaProperties);
    }

    /**
     * KafkaTemplate initialization
     *
     * @return KafkaTemplate
     */
    @Bean
    public KafkaTemplate kafkaTemplate() throws IOException {

        return new KafkaTemplate<>(producerConfigs());
    }



    public  Map<String, Object> onPremMixedProducerFactory(Properties prop) {

        Map<String, Object> properties = getCommonProducerProps(prop.getProperty("kafka.bootstrap-servers"));

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        addOnPremSchemaRegistryConfigs(properties,prop);

        addSaslProperties(properties, prop.getProperty("kafka.onprem-sasl-mechanism"),
                prop.getProperty("kafka.onprem-login-module"),
                prop.getProperty("kafka.username"),
                prop.getProperty("kafka.password"));
        addTruststoreProperties(properties, prop.getProperty("kafka.ssl.truststore-location"), prop.getProperty("kafka.ssl.truststore-password"));
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, prop.getProperty("kafka.ssl.keystore-location"));
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, prop.getProperty("kafka.ssl.keystore-password"));
        properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, prop.getProperty("key-password"));
        properties.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
        return properties;
    }

    private  Map<String, Object> getCommonProducerProps( String bootStrapServers) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, producerLinger);
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerRequestTimeout);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBatchSize);
        properties.put(ProducerConfig.SEND_BUFFER_CONFIG, producerSendBuffer);
        properties.put(ProducerConfig.ACKS_CONFIG, producerAcksConfig);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        return properties;
    }
    private static void  addOnPremSchemaRegistryConfigs(Map<String, Object> properties, Properties prop){
        properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, prop.getProperty("kafka.schema-registry-url"));
        properties.put("schema.registry.ssl.protocol", prop.getProperty("kafka.schema-registry-ssl-protocol"));
        properties.put("schema.registry.ssl.key.password", prop.getProperty("key-password"));
        properties.put("schema.registry.ssl.keystore.password", prop.getProperty("kafka.ssl.keystore-password"));
        properties.put("schema.registry.ssl.keystore.location", prop.getProperty("kafka.ssl.keystore-location"));
        properties.put("schema.registry.ssl.truststore.password", prop.getProperty("kafka.ssl.truststore-password"));
        properties.put("schema.registry.ssl.truststore.location", prop.getProperty("kafka.ssl.truststore-location"));
        properties.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS,false);

    }
    private static void addSaslProperties(Map<String, Object> properties, String mechanism, String loginModule, String username, String password) {
        if (!StringUtils.isEmpty(username)) {
            properties.put("security.protocol", "SASL_SSL");
            properties.put("sasl.mechanism", mechanism);
            String saslJassConfig = String.format("%s required username=\"%s\" password=\"%s\";", loginModule, username, password);
            properties.put("sasl.jaas.config", saslJassConfig);
            properties.put("spring.kafka.jaas.enabled",true);
        }
    }

    private static void addTruststoreProperties(Map<String, Object> properties, String location, String password) {
        if (!StringUtils.isEmpty(location)) {
            properties.put("ssl.truststore.location", location);
            properties.put("ssl.truststore.password", password);
        }
    }



}
