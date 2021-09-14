package net.apmm.mdm.ops.facility.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataRetrievalException extends RuntimeException{
    private String message;
}

