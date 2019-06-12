
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.ArrayList;
import java.util.List;


public class WordCountSQL {

    public static void main(String[] args) throws Exception{


        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);


        List list  =  new ArrayList();

        /*-------------------开始处理------------------------*/
        //开始处理成 flink 框架接收的格式
        String wordsStr = "Hello Flink Hello World";

        //分词
        String[] words = wordsStr.split("\\W+"); //匹配数字和字母下划线的多个字符

        for(String word : words){

            System.out.println(word);
            //初始化每个单词的频数
            WC wc = new WC(word, 1);
            //将所有WC对象添加到一个list中
            list.add(wc);
        }

        //从上下文把数据读进来
        DataSet<WC> input = env.fromCollection(list);
        /*-------------------结束处理------------------------*/


        //Flink中注册一个表
        tEnv.registerDataSet("WordCount", input, "word, frequency");

        Table table = tEnv.sqlQuery(
                "SELECT word, SUM(frequency) as frequency FROM WordCount GROUP BY word");

        //表放进来
        DataSet<WC> result = tEnv.toDataSet(table, WC.class);

        result.print();


    }

    public static class WC {

        public String word;//hello
        public long frequency;//1

        // public constructor to make it a Flink POJO
        public WC() {}

        public WC(String word, long frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "WC " + word + " " + frequency;
        }
    }

}