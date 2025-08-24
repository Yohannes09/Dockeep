package com.dockeep.document.mapper;

import com.dockeep.document.dto.DocumentDto;
import com.dockeep.document.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

    DocumentDto entityToDto(Document document);
}
