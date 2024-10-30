package passwords.api.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import passwords.api.user.entity.User;

public final class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private JSONObject requestBody;
    @Setter
    @Getter
    private User user;

    public MutableHttpServletRequest(HttpServletRequest request){
        super(request);
    }

}
