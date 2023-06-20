# backup

# 0.简介
备份 mysql 工具；可以同时备份多个 mysql 库的小工具；

临时写来用于服务器进行备份；临时使用工具；

主要用于临时写来给多个不同地址的数据库备份sql；

## 0.0. 你可以用来
你可以在一套环境上备份多个 MySQL 数据库；可以任意设置备份的时间段；

# 1. 使用过程

## 1.0. 下载
需要 java 17 环境进行运行；

## 1.1. 先配置文件 application.yml
application.yml 需要和 jar 文件放在同级目录下；
```yaml
server:
  port: 9999 # 需要占用端口

file:
  storage:
    directory: /Users/azwcl/Downloads/test/ # 备份的 sql 存储的目录；注意后面一定带上 / 
```

# 1.2. 配置 backup.json 文件
backup.json 需要放在 application.yml 中配置的目录下
```text
[
  {
    "host": "服务器 host",
    "port": xxx, # 服务器端口
    "dbName": "数字库名",
    "username": "数据库用户",
    "password": "数据库密码",
    "time": "0, */1 * * * ?" # 定时任务 cron 表达式
  }
]
```