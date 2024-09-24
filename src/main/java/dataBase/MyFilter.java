package dataBase;


import entity.Users;
import enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
@WebFilter("/*")
public class MyFilter implements Filter {

    private static final String AUTH_PATH = "/auth";
    private static final String INDEX_PATH = "/index.jsp";
    private static final String ERROR_PAGE = "/errorpage.jsp";
    private static final String WELCOME_PAGE = "/cabinet/welcome.jsp";
    private static final String ADMIN_PAGE = "/cabinet/admin/admin.jsp";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestURI = req.getRequestURI();
        System.out.println("Requested URI: " + requestURI);
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> userIdCookie = (cookies != null)
                ? Arrays.stream(cookies)
                .filter(cookie -> "userId".equals(cookie.getName()))
                .findFirst()
                : Optional.empty();
        String userId = userIdCookie.map(Cookie::getValue).orElse(null);
        System.out.println("User ID from cookie: " + userId);
        Optional<Users> optionalUser = Optional.empty();
        if (userId != null) {
            optionalUser = DB.getUserById(userId);
        }
        if (userId == null && optionalUser.isEmpty()) {
            System.out.println("No User ID found.");
            if (requestURI.contains(AUTH_PATH)) {
                System.out.println("Allowing access to /auth");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                System.out.println("Redirecting to /index.jsp");
                req.getRequestDispatcher(INDEX_PATH).forward(servletRequest, servletResponse);
            }
            return;
        }
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getCheckAccount()) {
                if (requestURI.contains("/admin") || user.getRole().equals(Role.ADMIN)) {
                    if (user.getRole().equals(Role.ADMIN)) {
                        System.out.println("Admin access granted.");
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        System.out.println("Access Denied: Admins only");
                        req.setAttribute("message", "Access Denied: Admins only");
                        req.getRequestDispatcher(ERROR_PAGE).forward(servletRequest, servletResponse);
                    }
                } else {
                    System.out.println("Allowing access to non-admin page.");
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                System.out.println("Invalid credentials");
                req.setAttribute("message", "Invalid credentials");
                req.getRequestDispatcher(ERROR_PAGE).forward(servletRequest, servletResponse);
            }
        } else {
            System.out.println("Block credentials");
            req.setAttribute("message", "Your account blocking");
            req.getRequestDispatcher(ERROR_PAGE).forward(servletRequest, servletResponse);
        }

    }


}
