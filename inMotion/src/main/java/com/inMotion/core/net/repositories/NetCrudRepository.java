package com.inMotion.core.net.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.inMotion.core.net.ResourceOperationType;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.JsonConnection;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;
import com.inMotion.core.net.repositories.delegates.*;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.core.objects.Instance;
import com.inMotion.core.objects.lists.List;
import com.inMotion.core.objects.lists.Pager;
import com.inMotion.core.objects.queries.Query;

import java.net.URL;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudRepository<T extends BaseObject> extends NetBaseRepository {

    private Class<T> builder = null;
    private String modelName = null;

    protected NetCrudRepository(Class<T> instanceType) {
        this(instanceType, instanceType.getSimpleName());
    }

    protected NetCrudRepository(Class<T> instanceType, String modelName) {
        this.builder = instanceType;
        this.modelName = modelName;
    }

    protected String getModelName() {
        return modelName;
    }

    protected void one(String guid, INetCrudOneRepositoryDelegate<T> delegate) {

        final NetCrudRepository<T> instance = this;
        final INetCrudOneRepositoryDelegate<T> callback = delegate;
        final String requestedGuid = guid;

        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(this.makeResource(guid, ResourceOperationType.Api), HttpMethod.Get, HttpContentType.Json);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                if (statusCode != 200) {
                    callback.netCrudRepositoryDidFail(instance, null);
                }

                T value = instance.makeObject(result);
                if (callback != null) {
                    if (value != null) {
                        callback.netCrudRepositoryDidGetOne(instance, value);
                    }
                    else {
                        callback.netCrudRepositoryDidFail(instance, null);
                    }
                }
            }


            @Override
            public void jsonConnectionDidSucceedWithNoResult(JsonConnection connection, int statusCode) {
                if (callback != null) {
                    callback.netCrudRepositoryDidGetNone(instance, requestedGuid);
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netCrudRepositoryDidFail(instance, error);
                }
            }
        });
    }

    // size, index, query
    protected void all(Integer size, Integer index, Query query, INetCrudManyRepositoryDelegate<T> delegate) {

        final NetCrudRepository<T> instance = this;
        final INetCrudManyRepositoryDelegate<T> callback= delegate;

        URL requestUrl = null;
        try {
            URL baseUrl = this.makeResource(ResourceOperationType.Api);

            StringBuilder params = new StringBuilder(String.format("?i=%d&size=%d", index, size));
            if (query != null) {

            }
            requestUrl = new URL(baseUrl, params.toString());
        }
        catch (Exception ex) {
            return; // TODO: this will cause some unexpected results.
        }


        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(requestUrl, HttpMethod.Get, HttpContentType.Json);
        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                if (statusCode != 200) {
                    callback.netCrudRepositoryDidFail(instance, null);
                }

                List<T> value = instance.makeList(result);
                if (callback != null) {
                    if (value != null) {
                        callback.netCrudRepositoryDidGetAll(instance, value);
                    }
                    else {
                        callback.netCrudRepositoryDidFail(instance, null);
                    }
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netCrudRepositoryDidFail(instance, error);
                }
            }
        });

    }


    protected void save(T entity, INetCrudSaveRepositoryDelegate<T> delegate) {
        final NetCrudRepository<T> instance = this;
        final INetCrudSaveRepositoryDelegate<T> callback = delegate;

        String guid = null;
        HttpMethod method = HttpMethod.Post;
        if (entity.getInstance() != null && entity.getInstance().getId() != null) {
            method = HttpMethod.Put;
            guid = entity.getInstance().getId();
        }

        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(this.makeResource(guid, ResourceOperationType.Api), method, HttpContentType.Json);
        request.addObject(entity);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                if (statusCode != 200) {
                    callback.netCrudRepositoryDidFail(instance, null);
                }

                T value = instance.makeObject(result);
                if (callback != null) {
                    if (value != null) {
                        callback.netCrudRepositoryDidSave(instance, value);
                    }
                    else {
                        callback.netCrudRepositoryDidFail(instance, null);
                    }
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netCrudRepositoryDidFail(instance, error);
                }
            }
        });
    }

    protected void delete(T entity, INetCrudDeleteRepositoryDelegate<T> delegate) {
        final NetCrudRepository<T> instance = this;
        final INetCrudDeleteRepositoryDelegate<T> callback = delegate;
        final T value = entity;

        if (entity.getInstance() == null || entity.getInstance().getId() == null) {
            return;
        }

        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(this.makeResource(entity.getInstance().getId(), ResourceOperationType.Api), HttpMethod.Delete, HttpContentType.Json);

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceedWithNoResult(JsonConnection connection, int statusCode) {
                if (callback != null) {
                    if (statusCode != 204) {
                        callback.netCrudRepositoryDidFail(instance, null);
                        return;
                    }
                    callback.netCrudRepositoryDidDelete(instance, value);
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netCrudRepositoryDidFail(instance, error);
                }
            }
        });
    }

    private URL makeResource(ResourceOperationType operation) {
        return this.makeResource(null, operation);
    }

    private URL makeResource(String id, ResourceOperationType operation) {
        String fragment = String.format("e/%s/%s/%s", operation.toString(), this.modelName, (id == null || id.length() < 1) ? "" : id);

        return this.makeUrl(fragment);
    }

    private T makeObject(JsonObject value) {
        if (this.builder == null)
            return null;

        Gson parser = new Gson();
        T result = parser.fromJson(value, builder);

        JsonObject meta = value.get("__i").getAsJsonObject();
        Instance instance = null;
        if (meta != null) {
            instance = Instance.parse(meta);
        }
        value.remove("__i"); // Get Rid of the meta

        result.init(instance, value);
        result.didDeserialize(); //


        return result;
    }

    private List<T> makeList(JsonObject value) {
        if (value == null)
            return null;

        JsonArray all = value.getAsJsonArray("r");
        JsonObject pagerValue = value.getAsJsonObject("__a");

        if (all == null || pagerValue == null)
            return null;

        java.util.List<T> records = new java.util.ArrayList<T>();
        for (JsonElement one : all) {
            records.add(this.makeObject(one.getAsJsonObject()));
        }
        Pager pager = Pager.parse(pagerValue);

        return  new List<T>(pager, records);
    }
}

