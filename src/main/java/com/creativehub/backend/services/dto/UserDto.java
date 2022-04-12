package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
	private final Long id;
	private final String username;
	private final String nickname;
	private final String email;
	private final Role role;
	private final CreatorDto creator;
	private final Set<Long> inspirerIds;
	private final Set<Long> fanIds;
}
