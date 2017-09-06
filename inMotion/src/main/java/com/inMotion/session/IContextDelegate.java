package com.inMotion.session;

import com.inMotion.core.Error;
import com.inMotion.entities.user.User;

/**
 * Created by sfbechtold on 9/7/15.
 */
public interface IContextDelegate {

    void contextLoginDidFail(Context session, Error error);
    void contextLoginDidSucceed(Context session, User user);

    void contextRefreshWillHappen(Context session);
    void contextRefreshDidFail(Context session, Error error);
    void contextRefreshDidSucceed(Context session, User user);

    void contextUserDidUpdate(Context session, User user);
    void contextUserWillLogout(Context session, User user);
    void contextUserDidLogout(Context session, User user);

}
