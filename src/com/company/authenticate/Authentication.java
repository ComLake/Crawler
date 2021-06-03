package com.company.authenticate;

public class Authentication implements AuthenticationInterface{
    private static Authentication authentication;
    private String token;
    private String refreshToken;
    private AccountHandle accountHandle;
    public static Authentication getInstance(){
        if (authentication==null){
            authentication = new Authentication();
        }
        return authentication;
    }

    public Authentication() {
        accountHandle = new AccountHandle();
        accountHandle.setTokenAndRefreshToken(this);
    }

    public void login(){
        accountHandle.loginAccount();
    }
    public void register(){
        accountHandle.createAccount();
        System.out.println("--------------------Verify account--------------------");
        accountHandle.loginAccount();
    }
    public void refreshToken(){
        accountHandle.setToken(token);
        accountHandle.setRefreshToken(refreshToken);
        accountHandle.refreshToken();
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public void updateToken(String token, String refreshToken) {
        setToken(token);
        setRefreshToken(refreshToken);
    }
}
