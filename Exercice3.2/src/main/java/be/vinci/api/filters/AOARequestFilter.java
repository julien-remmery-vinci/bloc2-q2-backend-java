package be.vinci.api.filters;

import be.vinci.domain.User;
import be.vinci.services.UserDataService;
import be.vinci.services.UserDataServiceImpl;
import be.vinci.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
@Singleton
@Provider
@AnonymousOrAuthorized
public class AOARequestFilter implements ContainerRequestFilter {
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
    private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
    @Inject
    private UserDataService myUserDataService;
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = requestContext.getHeaderString("Authorization");
        if (token == null) {
            requestContext.setProperty("user", null);
        } else {
            DecodedJWT decodedToken;
            try {
                decodedToken = this.jwtVerifier.verify(token);
            } catch (Exception e) {
                throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Malformed token : " + e.getMessage()).type("text/plain").build());
            }
            User authenticatedUser = myUserDataService.getOne(decodedToken.getClaim("user").asInt());
            if (authenticatedUser == null) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("You are forbidden to access this resource").build());
            }

            requestContext.setProperty("user", authenticatedUser);
        }
    }
}
