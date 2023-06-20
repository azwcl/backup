package com.azwcl.backup.interfaces.starter.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库备份 dto
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */

@Getter
@Setter
@ToString
public class DatabaseBackUpDTO {
    private String host;
    private Integer port;
    private String dbName;
    private String username;
    private String password;
    private String time;
}
