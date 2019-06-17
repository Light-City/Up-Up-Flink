# Up-Up-Flink

## 1.项目架构

| 目录名称 | 目录描述 | 目录名称    | 目录描述                                   |
| ----------- | ------------------------------------------ | ------------------------------------------ | ------------------------------------------ |
| [flink-simpleExample](./flink-simpleExample) | flink简单例子 | [wordcount](./flink-simpleExample/wordcount) | sock通信，监听端口内的单词统计             |
|  | |  |  |
| [flink-connectors](./flink-connectors) | flink-connectors例子 | [flink_SingleMysql](./flink-connectors/flink_SingleMysql) | kafka生成数据，flinkkafka消费数据，单条插入Mysql数据库 |
|  |  | [flink_BatchMysql](./flink-connectors/flink_BatchMysql) | kafka生成数据，flinkkafka消费数据，批量插入Mysql数据库 |
|  |  | [flink_BatchHBase](./flink-connectors/flink_BatchHBase) | kafka生成数据，flinkkafka消费数据，批量插入HBase数据库 |
| [flink-readSource](./flink-readSource) | flink源码阅读 | [WordCount](./flink-readSource/wordcount) | 流程序的 wordcount 和批程序的 wordcount 执行行行流程分析源码 |
|  |  |  |  |
| [flink-sql](./flink-sql) | flink-sql自理 | [WordCountSQL](./flink-sql/WordCountSQL) | wordcountSQL例子 |
|  |  |  |  |
| [flink-dataset](./flink-dataset) | flink-dataset学习 | [DataSource](./flink-dataset/DataSource) | 数据加载方式 |
|  |  | [DataSet](./flink-dataset/DataSet) | map、join、sort、join、retry的练习 |
|  |  | | |
|  |  | [DataStream](./flink-stream/datastream) |没有并行度的stream、有并行度的stream及自定义的stream(实现接口与继承类) |
| [flink-advanced](./flink-advanced)           | flink高级进阶 | [restart_distcache](./flink-advanced/restart_distcache) |重启策略和分布式缓存 |



> 备注：部分学习来源于https://github.com/zhisheng17/flink-learning



## 2.关于我

- 个人网站

    https://light-city.club
- 个人微信公众号

![wechat](./img/pub/wechat.jpg)
