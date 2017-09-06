package com.inMotion.session;

import com.inMotion.core.config.AppConfig;
import com.inMotion.entities.user.IUserRepositoryDelegate;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.UserRepository;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class Context implements IUserRepositoryDelegate {

    private Authorization authorization = null;
    private User user = null;
    private ArrayList<IContextDelegate> delegates = new ArrayList<IContextDelegate>();

    public boolean addResponder(IContextDelegate delegate){
        if (delegate == null) {
            return  false;
        }

        if (delegates.contains(delegate)){
            return  false;
        }

        delegates.add(delegate);
        return true;
    }

    public boolean removeResponder(IContextDelegate delegate) {

        if (delegates.contains(delegate)) {
            delegates.remove(delegate);
            return  true;

        }
        return false;
    }

    public void login(User user, String password) {
        UserRepository repository = new UserRepository(this);
        repository.login(user, password, false);
    }

    public void logout() {
        authorization = null;
        user = null;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public User getUser() {
        return user;
    }

    public static void init(android.content.Context context) {
        if (current == null){
            padlock.lock();
            if (current == null) {

                AppConfig.init(context); // Init the app config

                current = new Context();
                padlock.unlock();
            }
        }
    }

    // Singleton
    private static Lock padlock = new ReentrantLock();
    private static Context current = null;

    public static Context getCurrent() {

        return current;
    }


    // IUserRepositoryDelegate Members

    @Override
    public void userRepositoryAuthorizationDidUpdate(UserRepository repository, Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public void userRepositoryLoginDidSucceed(UserRepository repository, User user) {
        this.user = user;

        if (this.delegates != null) {
            for (IContextDelegate delegate : this.delegates) {
                delegate.contextLoginDidSucceed(this, user);
            }
        }
    }

    @Override
    public void userRepositoryLoginDidFail(UserRepository repository, User user) {

        if (this.delegates != null) {
            for (IContextDelegate delegate : this.delegates) {
                delegate.contextLoginDidFail(this, null);
            }
        }

    }
}
