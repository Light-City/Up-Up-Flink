import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * 1.使用广播状态，task任务之间不会相互通信
 * 2.广播状态中事件的通信在各个并发实例中可能不尽相同
 * 3.所有operator task都会快照下他们的广播状态
 * 4.广播变量存在俞内存中
 */
public class BroadCast2 {
    public static void main(String[] args)  throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);  //如果两次运行结果要一样，可以设置这里并行度为2

        String[] data1 = new String[] {"A","B","C","D","E","F","A"};
        Integer[] data2 = new Integer[] {1,2,3,3};
        DataStream<String> text1;
        DataStream<Integer> text2;

        text1 = env.fromElements(data1);
        text2 = env.fromElements(data2).broadcast();
        DataStream<String> TraWordProbability = text1
                .keyBy(new KeySelector<String, String>() {

                    @Override
                    public String getKey(String in) throws Exception {
                        // TODO Auto-generated method stub
                        return in;
                    }
                })
                .connect(text2)   //text2广播，text2修改 只有广播的一边可以修改广播状态的内容
                .flatMap(new CoFlatMapFunction<String, Integer, String>() {

                    int count = 0;
                    @Override
                    public void flatMap1(String input, Collector<String> out) throws Exception {
                        // TODO Auto-generated method stub
                        System.out.println("String.valueOf(count):" + String.valueOf(count));
                        System.out.println("input:"+input);
                        String b = input + String.valueOf(count);
                        System.out.println(b);
                    }

                    @Override
                    public void flatMap2(Integer input2, Collector<String> out) throws Exception {
                        // TODO Auto-generated method stub
                        count +=input2;
                        System.out.println("====================");
                        System.out.println(count);
                    }

                })
                .setParallelism(2);

        env.execute("test");
    }

}