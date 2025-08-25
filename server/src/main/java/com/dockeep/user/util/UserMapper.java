package com.dockeep.user.util;

import com.dockeep.user.model.User;
import com.dockeep.user.model.UserDto;
import com.dockeep.user.model.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto entityToDto(User user);
    UserPrincipal entityToPrincipal(User user);
}
