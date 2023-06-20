package com.azwcl.backup.domain.mysql.service;

import com.azwcl.backup.domain.mysql.converter.ToMySqlConnectionInfoConverter;
import com.azwcl.backup.domain.mysql.model.domainobject.DatabaseConnectionDO;
import com.azwcl.backup.infrastructure.client.MySqlClient;
import com.azwcl.backup.infrastructure.model.MySqlConnectionInfo;
import com.azwcl.backup.infrastructure.utils.FileUtil;
import com.azwcl.backup.infrastructure.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 数据库 mysql service
 *
 * @author azwcl
 * @date 2023/06/18
 * @since v0.0.1
 */

@Service
@RequiredArgsConstructor
public class MySqlService {
    private final FileUtil fileUtil;

    /**
     * 备份全量
     *
     * @param connection 数据库连接信息
     */
    public void fullBackUp(DatabaseConnectionDO connection) throws SQLException {
        MySqlConnectionInfo connectionInfo = ToMySqlConnectionInfoConverter.CONVERTER.toMySqlConnectionInfo(connection);
        MySqlClient client = new MySqlClient(connectionInfo);
        String sql = client.backUpAll();
        client.close();


        this.fileUtil.saveFile("", connection.getHost() + "-" + connection.getDbName() + "-" + TimeUtil.getDate() + TimeUtil.getTime() + ".sql", sql.getBytes());
    }
}
