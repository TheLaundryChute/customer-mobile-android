package com.inMotion.entities.user.profile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.JsonConnection;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;
import com.inMotion.core.net.repositories.NetBaseRepository;
import com.inMotion.session.Context;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class ProfileRepository extends NetBaseRepository {

    private static final String PROFILERESOURCE_PROFILE = "profile";

    private IProfileRepositoryDelegate delegate = null;

    public ProfileRepository(IProfileRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    public void getProfileForCurrentUser() {
        final ProfileRepository instance = this;

        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(PROFILERESOURCE_PROFILE, HttpMethod.Get, HttpContentType.Json);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {

                if (statusCode != 200) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().profileRepositoryDidFail(instance, null);
                    }
                    return;
                }

                Gson parser = new Gson();
                Profile profile = parser.fromJson(result, Profile.class);

                if (profile != null) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().profileRepositoryDidGetProfile(instance, Context.getCurrent().getUser(), profile);
                    }
                }
                else {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().profileRepositoryProfileNotFound(instance, Context.getCurrent().getUser());
                    }
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().profileRepositoryDidFail(instance, error);
                }
            }
        });
    }

    public IProfileRepositoryDelegate getDelegate() {
        return delegate;
    }

    public void save(Profile profile) {

        final ProfileRepository instance = this;
        final Profile data = profile;

        JsonConnection connection = new JsonConnection();

        HttpRequest request = this.manufactureRequest(PROFILERESOURCE_PROFILE, HttpMethod.Put, HttpContentType.Json);
        request.addObject(profile);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {

                if (statusCode != 204) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().profileRepositoryDidFail(instance, null);
                    }
                    return;
                }

                if (instance.getDelegate() != null) {
                    instance.getDelegate().profileRepositoryDidSave(instance, data);
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().profileRepositoryDidFail(instance, error);
                }
            }
        });
    }
}
