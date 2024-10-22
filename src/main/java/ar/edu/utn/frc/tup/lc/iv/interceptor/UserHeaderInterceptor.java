package ar.edu.utn.frc.tup.lc.iv.interceptor;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
/**
 * Intercepts POST and PUT requests to check for a valid 'x-user-id' header.
 * Stores the user ID in ThreadLocal during the request.
 * Returns a 400 Bad Request if the header is missing or invalid.
 * Exempts certain paths from validation.
 * Clears the ThreadLocal after the request completes.
 */
@Component
public class UserHeaderInterceptor implements HandlerInterceptor {
    /**
     * Stores the current user's ID for the duration of the request.
     */
    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();
    /**
     * Paths that are exempt from the 'x-user-id' validation.
     */
    private static final List<String> EXEMPT_PATHS = Arrays.asList(
    );
    /**
     * Validates the 'x-user-id' header for POST and PUT requests.
     * If invalid or missing, returns a 400 Bad Request response.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param handler the handler
     * @return true if the request can proceed, false if validation fails
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String method = request.getMethod();
        String path = request.getRequestURI();
        if (EXEMPT_PATHS.contains(path)) {
            return true;
        }

        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)
            || "DELETE".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) {
            String userId = request.getHeader("x-user-id");

            if (userId == null || userId.isEmpty() || !isValidUserId(userId)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                ErrorApi errorApi = ErrorApi.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(HttpServletResponse.SC_BAD_REQUEST)
                        .error("Bad Request")
                        .message("Missing Header: x-user-id")
                        .build();

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(errorApi);

                out.print(json);
                out.flush();
                return false;
            }
            currentUser.set(Long.parseLong(userId));
        }
        return true;
    }
    /**
     * Retrieves the current user ID stored in the ThreadLocal.
     *
     * @return the current user ID or null if not set
     */
    public static Long getCurrentUserId() {
        return currentUser.get();
    }
    /**
     * Cleans up the ThreadLocal after the request is complete.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param handler the handler
     * @param ex the exception if any occurred
     * @throws Exception in case of errors
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        currentUser.remove();
    }
    /**
     * Checks if the provided user ID is a valid number.
     *
     * @param userId the user ID to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidUserId(String userId) {
        try {
            Long.parseLong(userId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}