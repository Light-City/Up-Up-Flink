package DataSource;
/*
    获得一个execution environment
    加载/创建初始数据
    指定此数据的转换
    指定放置计算结果的位置
    触发程序执行
 */


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/*
读取数据：
    基于文件  DataSource.readTextFile(inputPath);
    基于集合  fromCollection(Collection); 这个用的最多！
 */

/*
    统计文件内的单词个数
    by 光城
 */


public class fromCollection {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        List data = new ArrayList<String>() {{
            add("hello world");
            add("Hello Flink");
        }};

        DataSource text = env.fromCollection(data);

        //groupBy中的0表示角标，代表(token,1)中的token。sum中1表示按照代表(token,1)中的1进行累加。类似于Python中的元组对应的index。
        AggregateOperator<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer()).groupBy(0).sum(1);
        counts.print();  // 默认会提交,不用加env.execute();
        /*
            写入本地文件，每个结果存入一个文件
         */
    }

    public static class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        /*
            String s    表示一行一行的数据
            Collector   我们对数据进行处理以后，都被collector收集，然后进行处理
        */
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] tokens = s.toLowerCase().split("\\W+");
            for (String token : tokens) {
                //String,Integer 单词，次数
                collector.collect(new Tuple2<String, Integer>(token,1));
            }

        }
    }

}
