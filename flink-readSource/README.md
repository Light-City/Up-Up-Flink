# Up-Up-Flink

## 1.[wordcount](./wordcount)

**流程序的 wordcount 和批程序的 wordcount 执行流程区别?**

| 批程序的 wordcount                                           | 流程序的 wordcount                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 使用`ExecutionEnvironment`(抽象类)<br />**<u>负责批程序的执行行行上下文</u>**。 | 使用`StreamExecutionEnvironment`(抽象类)<br />**<u>负责流程序的执行上下文</u>**。 |
| 使用**groupBy**，返回DataSet类型的数据。                     | 使用**keyBy**，返回KeyedStream类型的数据。                   |
| 不需要`env.execute()`。(原因：print 方方法内部其实是有调用`env.execute()` 方方法) | 需要`env.execute()`。                                        |

## 关于我

- 个人网站

    https://light-city.club
- 个人微信公众号

![wechat](../img/pub/wechat.jpg)
