package com.inMotion.session;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class Authorization {

    private String access_token = null;
    private String token_type = null;
    private int expires_in = -1;
    private String userName = null;
    private String organization = null;
    private Boolean isCustomer = true;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }


    public String createHeaderValue() {
        return this.getToken_type() + " " + this.getAccess_token();
    }

    //loginSignal
    //{"access_token":"HRBKx+pML0eW694Hip6dYA==FSuZrq1Q3kijWz9PtmDQGw==","token_type":"bearer","expires_in":1799,"as:client_id":"ngAuthApp","userName":"a@a.com","organization":"prostaff","isCustomer":"true",".issued":"Tue, 08 Sep 2015 02:01:15 GMT",".expires":"Tue, 08 Sep 2015 02:31:15 GMT"}
}
