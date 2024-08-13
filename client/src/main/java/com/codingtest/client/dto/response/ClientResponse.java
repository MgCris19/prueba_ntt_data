package com.codingtest.client.dto.response;

import com.codingtest.client.dto.ClientDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse{
    ClientDto clientDto;
}
