package DataSet.partition;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


import java.util.ArrayList;
import java.util.Iterator;


/** 解决数据倾斜方法：
 * rebalance 对数据集进行再平衡，重分区，消除数据倾斜
 * Hash-Partition 根据指定key的哈希值对数据集进行分区，某一key集中时还是会出现数据倾斜。
 * Range-Partition 根据指定的key对数据集进行范围分区
 * Custom Partitioning 自定义分区需要实现Partitioner接口
 *
 * by 光城
 */

public class Partition {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        ArrayList<Tuple2<Integer, String>> data = new ArrayList<Tuple2<Integer, String>>(){{
           add(new Tuple2<Integer, String>(1,"hello1"));
           add(new Tuple2<Integer, String>(1,"hello2"));
           add(new Tuple2<Integer, String>(1,"hello3"));
           add(new Tuple2<Integer, String>(2,"hello4"));
           add(new Tuple2<Integer, String>(3,"hello5"));
           add(new Tuple2<Integer, String>(1,"hello6"));
           add(new Tuple2<Integer, String>(1,"hello7"));
           add(new Tuple2<Integer, String>(4,"hello8"));
           add(new Tuple2<Integer, String>(1,"hello9"));
           add(new Tuple2<Integer, String>(5,"hello10"));
        }};

        DataSource<Tuple2<Integer, String>> text = env.fromCollection(data);

        //数据倾斜   数据量越大分布越均匀，每个分区分配的数量相差不大
        //rebalance会对数据自动进行分区
//        text.rebalance().mapPartition(new MapPartitionFunction<Tuple2<Integer, String>, Tuple2<Integer, String>>() {
//            @Override
//            public void mapPartition(Iterable<Tuple2<Integer, String>> values, Collector<Tuple2<Integer, String>> out) throws Exception {
//                Iterator<Tuple2<Integer, String>> iterator = values.iterator();
//                while (iterator.hasNext()) {
//                    Tuple2<Integer, String> next = iterator.next();
//                    System.err.println("当前线程id: " + Thread.currentThread().getId() + "," + next);
//
//                }
//            }
//        }).print();



        //解决数据倾斜 效果不是特别明显   当数据有1000万 -> 某个worker 有可能会出现500万 导致严重的数据倾斜 -> 因此哈希Partition比较适合：key比较规律 有一定规则  每个规则下的数量大体相等
//        text.partitionByHash(0).
//                mapPartition(new MapPartitionFunction<Tuple2<Integer, String>, Tuple2<Integer, String>>() {
//                    @Override
//                    public void mapPartition(Iterable<Tuple2<Integer, String>> values, Collector<Tuple2<Integer, String>> out) throws Exception {
//                        Iterator<Tuple2<Integer, String>> it = values.iterator();
//                        while (it.hasNext()) {
//                            Tuple2<Integer, String> next = it.next();
//
//                            System.err.println("当前线程id：" + Thread.currentThread().getId() + "," + next);
//                        }
//
//                    }
//                }).print();
        //有助于解决数据倾斜
//        text.partitionByRange(0).
//                mapPartition(new MapPartitionFunction<Tuple2<Integer, String>, Tuple2<Integer, String>>() {
//                    @Override
//                    public void mapPartition(Iterable<Tuple2<Integer, String>> values, Collector<Tuple2<Integer, String>> out) throws Exception {
//                        Iterator<Tuple2<Integer, String>> it = values.iterator();
//                        while (it.hasNext()) {
//                            Tuple2<Integer, String> next = it.next();
//
//                            System.err.println("当前线程id：" + Thread.currentThread().getId() + "," + next);
//                        }
//
//                    }
//                }).print();
        text.partitionCustom(new MyPartition(), 0).
                mapPartition(new MapPartitionFunction<Tuple2<Integer, String>, Tuple2<Integer, String>>() {
                    @Override
                    public void mapPartition(Iterable<Tuple2<Integer, String>> values, Collector<Tuple2<Integer, String>> out) throws Exception {
                        Iterator<Tuple2<Integer, String>> it = values.iterator();
                        while (it.hasNext()) {
                            Tuple2<Integer, String> next = it.next();

                            System.err.println("当前线程id：" + Thread.currentThread().getId() + "," + next);
                        }

                    }
                }).print();

    }
    //用户自己定义，用户对自己的数据 非常熟悉，我可以做到没有数据倾斜。
    public static class MyPartition implements Partitioner<Integer> {

        @Override
        public int partition(Integer key, int numPartitions) {

            System.err.println("分区总数：" + numPartitions);
            // key=1   rangepartion

            // 注意：上述id最大为5，此处不能填5，必须小于5。
            return key % 3;// 1 2 ... 1 2 3


        }
    }
}
