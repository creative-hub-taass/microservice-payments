package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
	private Long id;
	private String username;
	private String nickname;
	private String email;
	private Role role;
	private CreatorDto creator;
	private Set<Long> inspirerIds;
	private Set<Long> fanIds;
}
