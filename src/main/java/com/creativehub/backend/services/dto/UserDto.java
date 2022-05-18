package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto implements Serializable {
	private UUID id;
	private String username;
	private String nickname;
	private String email;
	private Role role;
	private CreatorDto creator;
	private Set<UUID> inspirerIds;
	private Set<UUID> fanIds;
}
