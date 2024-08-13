package com.codingtest.client;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.codingtest.client.entity.Client;
import com.codingtest.client.repository.ClientRepository;
import com.codingtest.client.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ClientApplicationTests {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetClientById() {
		// Arrange
		Long clientId = 1L;
		Client client = new Client();
		client.setId(clientId);
		when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

		// Act
		Optional<Client> result = clientRepository.findById(clientId);

		// Assert
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(clientId);
	}

	@Test
	void testSaveAccount() {
		// Arrange
		Client client = new Client();
		when(clientRepository.save(client)).thenReturn(client);

		// Act
		Client result = clientRepository.save(client);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(client);
	}
}