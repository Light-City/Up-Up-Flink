package DataSet.join;


import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.CrossOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/*
    内连接
    左外连接
    右外连接
    全连接
    笛卡尔积

    by 光城
 */

import java.util.ArrayList;

public class Join {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //table1 id,name

        ArrayList<Tuple2<Integer, String>> data1 = new ArrayList<Tuple2<Integer, String>>(){{
            add(new Tuple2<Integer, String>(1, "诸葛孔明"));
            add(new Tuple2<Integer, String>(3, "曹操"));
            add(new Tuple2<Integer, String>(4, "张飞"));
        }};

        //table2 id,city

        ArrayList<Tuple2<Integer, String>> data2 = new ArrayList<Tuple2<Integer, String>>(){{
            add(new Tuple2<Integer, String>(1, "北京"));
            add(new Tuple2<Integer, String>(2, "上海"));
            add(new Tuple2<Integer, String>(3, "深圳"));
        }};
        DataSource<Tuple2<Integer, String>> text1 = env.fromCollection(data1);
        DataSource<Tuple2<Integer, String>> text2 = env.fromCollection(data2);

        /**
         * 内连接
         *
         */

        //select id,name,city from table1 inner join table2 on table1.id=table2.id;
        text1.join(text2).where(0) //where(int)通过下标0，也就是id列进行join
                .equalTo(0)
                //with(JoinFunction<I1, I2, R> 3个参数分别代表table1、table2、定义好的表返回
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer,String,String>>() {
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> table1, Tuple2<Integer, String> table2) throws Exception {
                        return new Tuple3<Integer, String, String>(table1.f0, table1.f1, table2.f1); //表1的id、name及表2的city
                    }
                }).print();

        /** 输出：
         * (3,曹操,深圳)
         * (1,诸葛孔明,北京)
         */



        /**
         * 左外连接
         *
         * 右表有可能为null
         */

        text1.leftOuterJoin(text2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer,String,String>>() {
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> table1, Tuple2<Integer, String> table2) throws Exception {
                        if (table2 == null) {
                            return new Tuple3<>(table1.f0, table1.f1, null);
                        }else{
                            return new Tuple3<>(table1.f0, table1.f1, table2.f1);
                        }
                    }
                }).print();
        /**
         * 输出：
         *(3,曹操,深圳)
         * (1,诸葛孔明,北京)
         * (4,张飞,null)
         */

        /**
         * 右外连接
         *
         * 左表有可能为null
         */

        text1.rightOuterJoin(text2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer,String,String>>() {
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> table1, Tuple2<Integer, String> table2) throws Exception {
                        if (table1 == null) {
                            return new Tuple3<>(table2.f0, null, table2.f1);
                        }else{
                            return new Tuple3<>(table2.f0, table1.f1, table2.f1);
                        }
                    }
                }).print();
        /** 输出
         * (3,曹操,深圳)
         * (1,诸葛孔明,北京)
         * (2,null,上海)
         */


        /**
         * 全连接
         *
         * table1与table2都有可能为null
         */
        text1.fullOuterJoin(text2)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer,String,String>>() {
                    @Override
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> table1, Tuple2<Integer, String> table2) throws Exception {
                        if (table1 == null) {
                            return new Tuple3<>(table2.f0,null,table2.f1);
                        } else if (table2 == null) {
                            return new Tuple3<>(table1.f0, table1.f1, null);
                        } else {
                            return new Tuple3<>(table1.f0, table1.f1, table2.f1);
                        }
                    }

                }).print();
        /** 输出
         * (3,曹操,深圳)
         * (1,诸葛孔明,北京)
         * (2,null,上海)
         * (4,张飞,null)
         */

        /**
         * 笛卡尔积
         *
         */
        CrossOperator.DefaultCross<Tuple2<Integer, String>, Tuple2<Integer, String>> cross = text1.cross(text2);
        cross.print();
        /** 输出
         * ((1,诸葛孔明),(1,北京))
         * ((1,诸葛孔明),(2,上海))
         * ((1,诸葛孔明),(3,深圳))
         * ((3,曹操),(1,北京))
         * ((3,曹操),(2,上海))
         * ((3,曹操),(3,深圳))
         * ((4,张飞),(1,北京))
         * ((4,张飞),(2,上海))
         * ((4,张飞),(3,深圳))
         */
    }

}
