package com.azwcl.backup.domain.mysql.model.domainobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库连接 domain object
 *
 * @author azwcl
 * @date 2023/06/18
 * @since v0.0.1
 */

@Getter
@Setter
@ToString
public class DatabaseConnectionDO {

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
