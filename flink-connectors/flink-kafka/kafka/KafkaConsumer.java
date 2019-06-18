import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;


public class KafkaConsumer {

    public static void main(String[] args) throws Exception{

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");


        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("tt", new SimpleStringSchema(), properties);

        //从最早开始消费
        consumer.setStartFromLatest();


        DataStream<String> stream = env
                .addSource(consumer);
        stream.print();

        env.execute();

    }//main

}//
