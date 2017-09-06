package com.inMotion.core.net.repositories.funcs;

import android.text.format.DateFormat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.ResourceOperationType;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.JsonConnection;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;
import com.inMotion.core.net.repositories.NetBaseRepository;
import com.inMotion.core.net.repositories.delegates.*;
import com.inMotion.core.objects.Instance;
import com.inMotion.core.objects.lists.List;
import com.inMotion.core.objects.lists.Pager;

import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sfbechtold on 12/9/15.
 */
public abstract class NetFunction<TRequest extends Object, TResponse extends Object> extends NetBaseRepository {
    private String modelName = null;
    private Type mResponseType = null;

    protected NetFunction(Type responseType, String modelName) {
        this.modelName = modelName;
        this.mResponseType = responseType;
    }


    protected String getModelName() {
        return modelName;
    }

    protected void one(INetFunctionDelegate<TRequest, TResponse> delegate) {

        final NetFunction<TRequest, TResponse> instance = this;
        final INetFunctionDelegate<TRequest, TResponse> callback = delegate;


        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(this.makeResource(modelName, ResourceOperationType.Function), HttpMethod.Get, HttpContentType.Json);
        request.setAuthorization(com.inMotion.session.Context.getCurrent().getAuthorization());

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                if (statusCode != 200) {
                    callback.netFunctionDidFail(instance, null);
                }

                FuncResponse<TResponse> value = instance.makeObject(result);
                if (callback != null) {
                    if (value.isSuccess()) {
                        callback.netFunctionDidSucceed(instance, value);
                    }
                    else {
                        callback.netFunctionDidFail(instance, null);
                    }
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netFunctionDidFail(instance, error);
                }
            }
        });
    }

    public void execute(TRequest entity, INetFunctionDelegate<TRequest, TResponse> delegate) {
        final NetFunction<TRequest, TResponse> instance = this;
        final INetFunctionDelegate<TRequest, TResponse> callback = delegate;

        String functionName = this.getClass().getSimpleName();
        HttpMethod method = HttpMethod.Post;

        JsonConnection connection = new JsonConnection();
        HttpRequest request = this.manufactureRequest(this.makeResource(functionName, ResourceOperationType.Function), method, HttpContentType.Json);
        request.setAuthorization(com.inMotion.session.Context.getCurrent().getAuthorization());
        if (entity == null) {
            request.addObject(new JsonObject());
        } else {
            request.addObject(entity);
        }

        Log.d("Request: ", request.getData().toString());

        connection.send(request, new JsonConnectionDelegate() {
            @Override
            public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                if (statusCode != 200) {
                    callback.netFunctionDidFail(instance, null);
                }

                FuncResponse<TResponse> value = instance.makeObject(result);
                if (callback != null) {
                    callback.netFunctionDidSucceed(instance, value);
                    /*if (value.isSuccess()) {
                        callback.netFunctionDidSucceed(instance, value);
                    }
                    else {
                        callback.netFunctionDidFail(instance, value);
                    }*/
                }
            }

            @Override
            public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                if (callback != null) {
                    callback.netFunctionDidFail(instance, error);
                }
            }
        });
    }


    private URL makeResource(ResourceOperationType operation) {
        return this.makeResource(null, operation);
    }

    private URL makeResource(String functionName, ResourceOperationType operation) {
        String fragment = String.format("e/%s/%s/%s", operation.toString(), this.modelName, functionName);

        return this.makeUrl(fragment);
    }

    private static final String[] DATE_FORMATS = new String[] {
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };


    private class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            TimeZone tz = TimeZone.getTimeZone("UTC");

            for (String format : DATE_FORMATS) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat(format);
                    df.setTimeZone(tz);
                    return df.parse(jsonElement.getAsString() );
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }

    private FuncResponse<TResponse> makeObject(JsonObject value) {
        if (mResponseType == null)
            return null;

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson parser = builder.create();//new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();//new Gson();




        //TODO: This is temporary.  We shouldn't be responding different data shapes than expected, ever.
        boolean success = value.getAsJsonPrimitive("success").getAsBoolean();
        FuncResponse<TResponse> result = null;

        if (success) {
            try {
                result =  parser.fromJson(value, mResponseType);
                Log.d("testing", result.toString());
            }
            catch (Exception e) {
                result = null;
                Log.d("testing", e.getMessage(), e);
            }

        }

        if (!success || result == null){
            result = new FuncResponse<TResponse>();
            result.setSuccess(success);

            Type collectionType = new TypeToken<Collection<String>>(){}.getType();
            ArrayList<String> messages = new Gson().fromJson(value.get("messages"), collectionType);
            //ArrayList<String> messages = (ArrayList<String>)value.getAsJsonArray("messages");

            result.setMessages(messages);
        }
        //We need to know if we had success before we can parse the 'data'

       // result.init(instance, value);
        //result.didDeserialize(); //

        //FuncResponse<TResponse> result = new FuncResponse<TResponse>();
       // result.setData(data);
        return result;
    }

}

