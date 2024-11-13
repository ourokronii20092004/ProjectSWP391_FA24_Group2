/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class LoginWithGoogle {

    Models.GoogleConsant ggC = new Models.GoogleConsant();

    public String getGoogleOAuthLoginURL() {
        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + ggC.getGOOGLE_CLIENT_ID()
                + "&redirect_uri=" + ggC.getGOOGLE_REDIRECT_URI()
                + "&response_type=code"
                + "&scope=email%20profile"
                + "&approval_prompt=force";
    }

    public String getGoogleLoginURL() {
        return "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + ggC.getGOOGLE_CLIENT_ID()
                + "&redirect_uri=" + ggC.getGOOGLE_REDIRECT_URI()
                + "&response_type=code"
                + "&scope=email profile openid"
                + "&approval_prompt=force";
    }

    public String[] getInforFromCode(String code) {
        String[] infor = new String[3];
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://oauth2.googleapis.com/token");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("code", code);
            parameters.put("client_id", ggC.getGOOGLE_CLIENT_ID());
            parameters.put("client_secret", ggC.getGOOGLE_CLIENT_SECRET());
            parameters.put("redirect_uri", ggC.getGOOGLE_REDIRECT_URI());
            parameters.put("grant_type", ggC.getGOOGLE_GRANT_TYPE());
            String jsonParameters = mapToJson(parameters);

            StringEntity entity = new StringEntity(jsonParameters);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(responseBody);
            infor = extractInforFromToken(responseBody);

        } catch (Exception e) {
        }
        return infor;
    }

    private String mapToJson(Map<String, String> map) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String[] extractInforFromToken(String responseBody) {
        String idToken = new Gson().fromJson(responseBody, JsonObject.class).get("id_token").getAsString();
        String[] idTokenParts = idToken.split("\\.");
        String encodedPayload = idTokenParts[1];
        String payload = new String(Base64.getDecoder().decode(encodedPayload));
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();

        // Lấy email từ payload
        System.out.println(payloadObject);
        String email = payloadObject.get("email").getAsString();
        String name = payloadObject.get("name").getAsString();
        String picture = payloadObject.get("picture").getAsString();
        System.out.println(email);
        System.out.println(name);
        System.out.println(picture);
        String[] result = new String[3];
        result[0] = email;
        result[1] = name;
        result[2] = picture;
        return result;
    }
}
