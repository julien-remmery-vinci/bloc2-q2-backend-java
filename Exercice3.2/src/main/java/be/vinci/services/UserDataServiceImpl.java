package be.vinci.services;

import be.vinci.domain.DomainFactory;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import be.vinci.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;

import java.util.List;

public class UserDataServiceImpl implements UserDataService {
    @Inject
    private DomainFactory domainFactory;
    private static final String COLLECTION_NAME = "users";
    private static final Json<User> jsonDB = new Json<>(User.class);
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public List<User> getAll() {
        return jsonDB.parse(COLLECTION_NAME);
    }

    @Override
    public User getOne(int id) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    @Override
    public User getOne(String login) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getLogin().equals(login)).findAny().orElse(null);
    }

    @Override
    public User createOne(User item) {
        var items = jsonDB.parse(COLLECTION_NAME);
        item.setId(nextItemId());
        items.add(item);
        jsonDB.serialize(items, COLLECTION_NAME);
        return item;
    }

    @Override
    public int nextItemId() {
        var items = jsonDB.parse(COLLECTION_NAME);
        if (items.isEmpty())
            return 1;
        return items.get(items.size() - 1).getId() + 1;
    }

    @Override
    public ObjectNode login(String login, String password) {
        User user = getOne(login);
        if (user == null || !user.checkPassword(password))
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            return jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

    @Override
    public ObjectNode register(String login, String password) {
        User tempUser = getOne(login);
        if (tempUser != null) // the user already exists !
            return null;
        tempUser = domainFactory.getUser();
        tempUser.setLogin(login);
        tempUser.setPassword(tempUser.hashPassword(password));

        User user = createOne(tempUser);
        if (user == null)
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            return jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

}
