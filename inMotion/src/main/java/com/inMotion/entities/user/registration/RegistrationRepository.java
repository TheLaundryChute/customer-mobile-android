package com.inMotion.entities.user.registration;

import com.google.gson.JsonObject;
import com.inMotion.core.config.AppConfig;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.JsonConnection;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;
import com.inMotion.core.net.repositories.NetBaseRepository;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class RegistrationRepository extends NetBaseRepository {

    private static final String REGISTRATIONRESOURCE_REGISTER = "account/register";

    private IRegistrationRepositoryDelegate delegate = null;

    public RegistrationRepository(IRegistrationRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    public IRegistrationRepositoryDelegate getDelegate() {
        return delegate;
    }

    public void register(Registration registration) {
        JsonConnection connection = new JsonConnection();
        final RegistrationRepository instance = this;
        final Registration data = registration;

        registration.setIsCustomer(AppConfig.getCurrent().getIsCustomer());
        registration.setOrganization(AppConfig.getCurrent().getOrganization());

        HttpRequest request = this.manufactureRequest(REGISTRATIONRESOURCE_REGISTER, HttpMethod.Post, HttpContentType.Json);
        request.addObject(registration);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {

                if (statusCode != 200) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().registrationRepositoryDidFail(instance, null);
                    }
                    return;
                }


                if (result.get("userId").getAsString() != null) {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().registrationRepositoryDidSucceeded(instance, data);
                    }
                }
                else {
                    if (instance.getDelegate() != null) {
                        instance.getDelegate().registrationRepositoryDidFail(instance, null);
                    }
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().registrationRepositoryDidFail(instance, error);
                }
            }
        });
    }
}
