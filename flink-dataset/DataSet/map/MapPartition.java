package DataSet.map;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import org.apache.flink.api.java.operators.MapPartitionOperator;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
    mapPartition非单词字符分割收集打印
    by 光城
 */

public class MapPartition {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//        List list = new ArrayList<String>(){{
//            add("Hello");add("hh");add("Hello");add("Flink");}};

        List list = new ArrayList<String>();
        list.add("hello world");
        list.add("hello flink");
        DataSource<String> text = env.fromCollection(list);

        MapPartitionOperator<String, String> mapPartition = text.mapPartition(new MapPartitionFunction<String, String>() {
            //用户输入需要迭代，由收集器进行收集
            public void mapPartition(Iterable<String> values, Collector<String> out) throws Exception {
                Iterator<String> iterator = values.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    String[] split = next.split("\\W+"); // \\W+ 匹配任何非单词字符。
                    for (String word : split) {
                        out.collect(word);
                    }
                }
            }
        });
        mapPartition.print();
    }
}
