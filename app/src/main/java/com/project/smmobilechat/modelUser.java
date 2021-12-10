package com.project.smmobilechat;

public class modelUser {

    private String userName;
    private String userEmail;
    private String userPass;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public modelUser(String userid,String username, String email){
        this.userId = userid;
        this.userName= username;
        this.userEmail= email;
    }


}
