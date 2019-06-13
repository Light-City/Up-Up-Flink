package DataSet.sort;

import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;

/**
 * 组内排序与不分组排序
 *
 * by 光城
 */
public class Sort {
    public static void main(String[] args) throws Exception {
        //获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        ArrayList<Tuple2<Integer, String>> data = new ArrayList<Tuple2<Integer, String>>(){{
            add(new Tuple2<Integer, String>(5,"cq"));
            add(new Tuple2<Integer, String>(5,"xm"));
            add(new Tuple2<Integer, String>(1,"bj"));
            add(new Tuple2<Integer, String>(1,"sz"));
            add(new Tuple2<Integer, String>(1,"gz"));
            add(new Tuple2<Integer, String>(2,"sh"));
            add(new Tuple2<Integer, String>(2,"nj"));
        }};



        DataSource<Tuple2<Integer, String>> text = env.fromCollection(data);

        System.out.println("==================获取前3条数据，按照数据插入的顺序======================");
        //获取前3条数据，按照数据插入的顺序
        text.first(3).print();
        /** 输出
         * (5,cq)
         * (5,xm)
         * (1,bj)
         */
        System.out.println("==================根据数据中的第一列进行分组，获取每组的前2个元素======================");
        //根据数据中的第一列进行分组，获取每组的前2个元素
        //注意：只分组了，并没有排序
        text.groupBy(0).first(2).print();
        /** 输出
         * (1,bj)
         * (1,sz)
         * (5,cq)
         * (5,xm)
         * (2,sh)
         * (2,nj)
         */
        System.out.println("==================根据数据中的第一列分组，并根据第二列组内排序[降序]，获取每组的前2个元素======================");
        //根据数据中的第一列分组，并根据第一列组内排序[升序]，获取每组的前2个元素
        text.groupBy(0).sortGroup(1, Order.DESCENDING).first(2).print();

        /** 输出
         * (1,sz)
         * (1,gz)
         * (5,xm)
         * (5,cq)
         * (2,sh)
         * (2,nj)
         */

        System.out.println("==================不分组，全局排序获取集合中的前5个元素，针对第一个元素升序，第二个元素降序======================");
        //不分组，全局排序获取集合中的前3个元素，针对第一个元素升序，第二个元素倒序
        text.sortPartition(0, Order.ASCENDING).sortPartition(1, Order.DESCENDING).first(5).print();
        /** 输出
         * (1,sz)
         * (1,gz)
         * (1,bj)
         * (2,sh)
         * (2,nj)
         */
    }
}
