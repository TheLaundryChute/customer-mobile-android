package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.net.repositories.funcs.FuncRequest;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetFunctionDelegate<TRequest extends Object, TResponse extends Object> {

    void netFunctionDidFail(NetFunction<TRequest, TResponse> function, com.inMotion.core.Error error);
    void netFunctionDidSucceed(NetFunction<TRequest, TResponse> function, FuncResponse<TResponse> result);

}
