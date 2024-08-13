package com.codingtest.client.dto.request;

import com.codingtest.client.dto.ClientDto;
import com.codingtest.client.entity.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotNull
    @Valid
    ClientDto clientDto;
}
