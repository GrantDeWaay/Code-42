package coms309.api.dataobjects;

import coms309.database.dataobjects.User;

public class ApiUserLogin extends ApiUser {

    private String token;

    public ApiUserLogin(User u) {
        super(u);
    }

    public ApiUserLogin(Long id, String username, String firstName, String lastName, String password, String email, String type, String token) {
        super(id, username, firstName, lastName, password, email, type);
        this.token = token;
    }

    
    /** 
     * @return String
     */
    public String getToken() {
        return token;
    }

    
    /** 
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

}
