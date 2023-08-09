package oladejo.mubarak.niquestore.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
    private int status;
}
