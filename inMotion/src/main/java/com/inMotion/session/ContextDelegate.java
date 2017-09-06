package com.inMotion.session;

import com.inMotion.core.*;
import com.inMotion.core.Error;
import com.inMotion.entities.user.User;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class ContextDelegate implements IContextDelegate {

    @Override
    public void contextLoginDidFail(Context session, com.inMotion.core.Error error) {

    }

    @Override
    public void contextLoginDidSucceed(Context session, User user) {

    }

    @Override
    public void contextRefreshWillHappen(Context session) {

    }

    @Override
    public void contextRefreshDidFail(Context session, Error error) {

    }

    @Override
    public void contextRefreshDidSucceed(Context session, User user) {

    }

    @Override
    public void contextUserDidUpdate(Context session, User user) {

    }

    @Override
    public void contextUserWillLogout(Context session, User user) {

    }

    @Override
    public void contextUserDidLogout(Context session, User user) {

    }
}
