package datastream;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * Created by 光城
 */
//使用并行度为1的source
public class MyNoParalleSource implements SourceFunction<Long> {  //并行度只能为1

    private long count = 1L;

    private boolean isRunning = true;

    /**
     * 主要的方法
     * 启动一个source
     * 大部分情况下，都需要在这个run方法中实现一个循环，这样就可以循环产生数据了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while(isRunning){
            ctx.collect(count);
            count++;
//            if (count == 50) {
//                return;
//            }
            //每秒产生一条数据
            Thread.sleep(1000);
        }
    }
    //取消一个cancel的时候会调用的方法
    @Override
    public void cancel() {
        isRunning = false;
    }
}
