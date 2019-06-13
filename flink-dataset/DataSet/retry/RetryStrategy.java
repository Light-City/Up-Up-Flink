package DataSet.retry;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * 容错：容错是通过重试失败的执行来实现，要激活容错，请执行重试次数设置大于零的值，常见的选择是值为3
 * env.setNumberOfExecutionRetries(3) 该方法被废除，使用重试延迟的方法env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3,5000));
 * 重试延迟：执行重试可以配置为延迟。在一定延迟之后进行执行。
 */

public class RetryStrategy {

    public static void main(String[] args) {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        env.setNumberOfExecutionRetries(3);  // 容错
        // 整个任务如果失败，重试3次，间隔5秒
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3,5000));


    }
}
