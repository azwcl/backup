package com.azwcl.backup.domain.mysql.converter;

import com.azwcl.backup.domain.mysql.model.domainobject.DatabaseConnectionDO;
import com.azwcl.backup.infrastructure.model.MySqlConnectionInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * --> MySqlConnectionInfo
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */
@Mapper
public interface ToMySqlConnectionInfoConverter {
    ToMySqlConnectionInfoConverter CONVERTER = Mappers.getMapper(ToMySqlConnectionInfoConverter.class);

    /**
     * --> MySqlConnectionInfo
     *
     * @param connection DatabaseConnectionDO
     * @return MySqlConnectionInfo
     */
    MySqlConnectionInfo toMySqlConnectionInfo(DatabaseConnectionDO connection);
}
