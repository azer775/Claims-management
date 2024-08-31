package org.example.gestionsinistre.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExeptionResponse {
    private int businessErrorCode;
    private String businessExeptionDesc;
    private String error;
    private Set<String> validationErrors;
    private Map<String,String> errors;

}
