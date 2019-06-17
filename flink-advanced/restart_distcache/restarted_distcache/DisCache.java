package restarted_distcache;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.configuration.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DisCache {

    public static void main(String[] args) throws Exception {
        //获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //注册一个文件,可以使用hdfs或者s3上的文件
        env.registerCachedFile("text", "test.txt");

        final DataSource<String> data = env.fromElements("a", "b", "c", "d");
        MapOperator<String, String> result = data.map(new RichMapFunction<String, String>() {
            private ArrayList<String> datalist = new ArrayList<>();

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                //使用文件
                File myFile = getRuntimeContext().getDistributedCache().getFile("test.txt");
                List<String> lines = FileUtils.readLines(myFile);
                for (String line : lines) {
                    this.datalist.add(line);
                    System.out.println("分布式缓存为:" + line);
                }
            }

            @Override
            public String map(String value) throws Exception {
                //在这里就可以使用datalist
                System.out.println("使用datalist:" + datalist + "----------------" + value);
                //业务逻辑
                return datalist + ":" + value;
            }
        });
        result.printToErr();

    }
}
