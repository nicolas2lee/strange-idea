package tao.strange.idea.http.session.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class HelloController {

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        final HttpSession session = request.getSession();
        final String sessionId = session.getId();
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) count = 0;
        else count++;
        session.setAttribute("count", count);
        final String message = String.format("The session id is: %s, with count: %d", sessionId, count);
        System.out.println(message);
        return message;
    }


    @GetMapping("/firebase")
    public String testFirebase(HttpServletRequest request) throws FirebaseMessagingException, IOException {
        final String resource = this.getClass().getClassLoader().getResource("google-services.json").toString();

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/xinrui/tao/apps/github/strange-idea/http-session/src/main/resources/google-services.json"));
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "ery9vnm2lgi:apa91bhxdxr6ra0hpip_vs3orz9xsva_fx6ufye1f_xvwi6zqe3xru8hmhvg1be54qnggmxpkyfsixodj-jmpz-k6kh--jf_cu0syw2etv9b1ttccccbawxdjnbykacqaeqvsny3vmd3";

// See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .build();

// Send a message to the device corresponding to the provided
// registration token.
        String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return "";
    }
}
