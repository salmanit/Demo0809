package com.sage.demo0809.bean;

/**
 * Created by Sage on 2016/9/6.
 */
public class LoginReply extends BaseError {
    private String _id;
    private String password;
    private int logintype;
    private long dt_registration;
    private long dt_lastLogin;
    private String sessionid;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLogintype() {
        return logintype;
    }

    public void setLogintype(int logintype) {
        this.logintype = logintype;
    }

    public long getDt_registration() {
        return dt_registration;
    }

    public void setDt_registration(long dt_registration) {
        this.dt_registration = dt_registration;
    }

    public long getDt_lastLogin() {
        return dt_lastLogin;
    }

    public void setDt_lastLogin(long dt_lastLogin) {
        this.dt_lastLogin = dt_lastLogin;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
