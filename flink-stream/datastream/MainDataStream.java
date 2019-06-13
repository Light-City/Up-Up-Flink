package datastream;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;


class MainDataStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //获取数据源
        // 没有并行度
//        DataStreamSource<Long> text = env.addSource(new MyNoParalleSource()).setParallelism(1); //注意：针对此source，并行度只能设置为1
        // 有并行度
        DataStreamSource<Long> text = env.addSource(new MyParalleSource()).setParallelism(2); //注意：针对此source，并行度只能设置为2

        SingleOutputStreamOperator<Long> map = text.map(new MapFunction<Long, Long>() {

            @Override
            public Long map(Long value) throws Exception {
                System.out.println("receive data is " + value);
                return value;
            }
        });
        // 每隔2秒中打印一下数据
        SingleOutputStreamOperator<Long> sum = map.timeWindowAll(Time.seconds(2)).sum(0);
        sum.print().setParallelism(1);
        String jobName = MainDataStream.class.getSimpleName();
        env.execute(jobName);
    }
}
