package com.inMotion.entities.user;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inMotion.core.config.AppConfig;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.JsonConnection;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;
import com.inMotion.core.net.repositories.NetBaseRepository;
import com.inMotion.session.Authorization;

import java.util.HashMap;

/**
 * Created by sfbechtold on 9/7/15.
 */



interface IUserRepositoryGetLoggedInUserDelegate {
    void succeeded(User user);
    void failed();
}

public class UserRepository extends NetBaseRepository {

    private static final String USERRESOURCE_LOGIN = "token";
    private static final String USERRESOURCE_CURRENTUSER = "current-user";
    private static final String USERRESOURCE_CHANGEPASSWORD = "account/updatePassword";

    private  IUserRepositoryDelegate delegate = null;

    public UserRepository(IUserRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    public IUserRepositoryDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(IUserRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    public void login(User user, String password, boolean rememberMe) {
        JsonConnection connection = new JsonConnection();

        final UserRepository instance = this;
        final User currentUser = user;

        connection.send(this.makeLoginRequest(user, password), new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {

                if (statusCode != 200) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().userRepositoryLoginDidFail(instance, currentUser);
                    }
                    return;
                }

                Gson parser = new Gson();
                Authorization authorization = parser.fromJson(result, Authorization.class);

                if (authorization == null || authorization.getAccess_token() == null || authorization.getToken_type() == null) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().userRepositoryLoginDidFail(instance, currentUser);
                    }
                    return;
                }

                if (instance.getDelegate() != null) {
                    instance.getDelegate().userRepositoryAuthorizationDidUpdate(instance, authorization);
                }

                instance.getUser(new IUserRepositoryGetLoggedInUserDelegate() {
                    @Override
                    public void succeeded(User user) {
                        if (instance.getDelegate() != null) {
                            instance.getDelegate().userRepositoryLoginDidSucceed(instance, user);
                        }
                    }

                    @Override
                    public void failed() {
                        instance.getDelegate().userRepositoryLoginDidFail(instance, currentUser);
                    }
                }, authorization);

                //Log.d("inMotion.Core", result.toString());
                //Log.d("inMotion.Core", "Json Connection Success method was not implemented by delegate");
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().userRepositoryLoginDidFail(instance, currentUser);
                }
            }
        });
    }


    private void getUser(final IUserRepositoryGetLoggedInUserDelegate callback, Authorization authorization) {
        JsonConnection connection = new JsonConnection();

        final IUserRepositoryGetLoggedInUserDelegate delegate = callback;

        HttpRequest request = this.manufactureRequest(USERRESOURCE_CURRENTUSER, HttpMethod.Get, HttpContentType.Json);
        if (authorization != null) {
            request.setAuthorization(authorization);
        }
        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {

                if (statusCode != 200) {
                    if (callback != null)
                        callback.failed();
                    return;
                }

                Gson parser = new Gson();
                User user = parser.fromJson(result.getAsJsonObject("user"), User.class);

                if (user != null && callback != null) {
                    callback.succeeded(user);
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null)
                    callback.failed();
            }
        });


    }

    private HttpRequest makeLoginRequest(User user, String password) {
        try {

            HttpRequest request = this.manufactureRequest(USERRESOURCE_LOGIN, HttpMethod.Post, HttpContentType.Form);
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("grant_type", "password");
            data.put("username", user.getEmailAddress());
            data.put("password", password);
            data.put("client_id", AppConfig.getCurrent().getClientId());
            data.put("organization", AppConfig.getCurrent().getOrganization());
            data.put("isCustomer", AppConfig.getCurrent().getIsCustomer().toString());
            request.appendData(data);

            return request;
        }
        catch (Exception ex) {
            Log.d("inMotion.core", "Exception making HTTP request for user repo", new Throwable(ex.getMessage()));
            return null;
        }

    }
}


