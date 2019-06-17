import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BroadCast1 {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<Integer> broadcast = env.fromElements(1, 2, 3);
        DataSource<String> data = env.fromElements("a", "b");

        data.map(new RichMapFunction<String, String>() {
            private List list = new ArrayList();

            @Override
            public void open(Configuration parameters) throws Exception {
                Collection<Integer> broadcast = getRuntimeContext().getBroadcastVariable("number");
                list.addAll(broadcast);
            }

            @Override
            public String map(String val) throws Exception {
                return val + ":" + list;
            }
        }).withBroadcastSet(broadcast, "number").print();
    }
}
