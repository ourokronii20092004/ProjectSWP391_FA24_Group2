/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class GoogleConsant {

    private final String GOOGLE_CLIENT_ID = "";
    private final String GOOGLE_CLIENT_SECRET = "";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/LoginWithGoogleController";
    private final String GOOGLE_GRANT_TYPE = "authorization_code";

    public String getGOOGLE_CLIENT_ID() {
        return GOOGLE_CLIENT_ID;
    }

    public String getGOOGLE_CLIENT_SECRET() {
        return GOOGLE_CLIENT_SECRET;
    }

    public String getGOOGLE_REDIRECT_URI() {
        return GOOGLE_REDIRECT_URI;
    }

    public String getGOOGLE_GRANT_TYPE() {
        return GOOGLE_GRANT_TYPE;
    }
    
}
