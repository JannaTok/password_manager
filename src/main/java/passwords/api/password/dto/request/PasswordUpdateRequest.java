package passwords.api.password.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordUpdateRequest {
    @NotEmpty
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
