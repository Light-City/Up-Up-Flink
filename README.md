# Up-Up-Flink

## 1.项目架构

| 目录名称 | 目录描述 | 目录名称    | 目录描述                                   |
| ----------- | ------------------------------------------ | ------------------------------------------ | ------------------------------------------ |
| [flink-simpleExample](./flink-simpleExample) | flink简单例子 | [wordcount](./flink-simpleExample/wordcount) | sock通信，监听端口内的单词统计             |
|  | |  |  |
| [flink-connectors](./flink-connectors) | flink-connectors例子 | [flink_SingleMysql](./flink-connectors/flink_SingleMysql) | kafka生成数据，flinkkafka消费数据，单条插入Mysql数据库 |
|  |  | [flink_BatchMysql](./flink-connectors/flink_BatchMysql) | kafka生成数据，flinkkafka消费数据，批量插入Mysql数据库 |
|  |  | [flink_BatchHBase](./flink-connectors/flink_BatchHBase) | kafka生成数据，flinkkafka消费数据，批量插入HBase数据库 |
| [flink-readSource](./flink-readSource) | flink源码阅读 | [wordcount](./flink-readSource/wordcount) | 流程序的 wordcount 和批程序的 wordcount 执行行行流程分析源码 |

> 备注：部分学习来源于https://github.com/zhisheng17/flink-learning

## 关于我

- 个人网站

    https://light-city.club
- 个人微信公众号

![wechat](./img/pub/wechat.jpg)
