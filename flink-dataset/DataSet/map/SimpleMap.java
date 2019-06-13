package DataSet.map;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;

import java.util.ArrayList;
import java.util.List;

/*
最简单的DataSource
by 光城
 */


public class SimpleMap {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//        List list = new ArrayList<String>(){{
//            add("Hello");add("hh");add("Hello");add("Flink");}};

        List list = new ArrayList<String>();
        list.add("hello world");
        list.add("hello flink");
        DataSource<String> text = env.fromCollection(list);
        MapOperator<String, String> map = text.map(new MapFunction<String, String>() {
            public String map(String value) throws Exception {
                return value;
            }
        });

        map.print();
    }

}
