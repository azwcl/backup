package com.azwcl.backup.application.scheduler.converter;

import com.azwcl.backup.domain.mysql.model.domainobject.DatabaseConnectionDO;
import com.azwcl.backup.interfaces.starter.dto.DatabaseBackUpDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * --> DatabaseConnectionDO
 *
 * @author azwcl
 * @date 2023/06/19
 * @since v0.0.1
 */
@Mapper
public interface ToDatabaseConnectionDoConverter {
    ToDatabaseConnectionDoConverter CONVERTER = Mappers.getMapper(ToDatabaseConnectionDoConverter.class);

    DatabaseConnectionDO toDatabaseConnectionDo(DatabaseBackUpDTO database);
}
