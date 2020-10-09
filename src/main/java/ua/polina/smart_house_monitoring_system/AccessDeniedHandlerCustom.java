package ua.polina.smart_house_monitoring_system;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Access denied handler.
 */
@AllArgsConstructor
public class AccessDeniedHandlerCustom implements AccessDeniedHandler {
    private String errorPage;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect(errorPage);
    }

    /**
     * Sets an error page.
     *
     * @param errorPage the error page
     */
    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
