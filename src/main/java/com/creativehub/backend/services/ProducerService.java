package com.creativehub.backend.services;

import java.util.UUID;

public interface ProducerService {
	void sendMessage(UUID id);
}