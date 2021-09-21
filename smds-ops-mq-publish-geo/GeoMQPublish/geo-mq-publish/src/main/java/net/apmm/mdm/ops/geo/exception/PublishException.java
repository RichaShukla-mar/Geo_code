package net.apmm.mdm.ops.geo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublishException extends RuntimeException {

    private String message;
}
