package com.azwcl.backup.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * MySQL 连接信息
 *
 * @author azwcl
 * @date 2023/06/18
 * @since v0.01
 */


@Getter
@Setter
@ToString
public class MySqlConnectionInfo {

    /**
     * host
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
