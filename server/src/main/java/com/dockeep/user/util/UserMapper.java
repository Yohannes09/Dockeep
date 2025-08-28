package com.dockeep.user.util;

import com.authmat.tool.events.NewUserEvent;
import com.dockeep.user.model.User;
import com.dockeep.user.model.UserDto;
import com.dockeep.user.model.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto entityToDto(User user);
    User dtoToEntity(UserDto userDto);
    UserPrincipal entityToPrincipal(User user);
    User eventToEntity(NewUserEvent event);
}
