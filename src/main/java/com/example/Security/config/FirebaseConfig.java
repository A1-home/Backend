package com.example.Security.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class FirebaseConfig {
    @PostConstruct
    public void init() {
        try {

            String firebaseCredentials = """
            {
             "type": "service_account",
                    "project_id": "mystore-eca03",
                    "private_key_id": "540733c14819e111be016ad105d8218baba4ee6c",
                    "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCk1axLDSrVROHl\niSkclQYkF1VX/hB01KjMBdpfHHeGjDAvk9qvMy/L92pd96lnu89KSIVPl6hu+8o8\njiFCAUGc9GO5H+h8BtSFhg1U/o8dULriOL2LZgfu9z5WJ2DVi1nhfTj0P/fyCgY0\nujFqjSuDbPqh5bUEwrjLt+db3zv2UzsjejVbyWtNVu5/xIaEk6/rqcVLl3V/cueS\n7cQu/1CKOfU8F5tDlvzrgQAIZ0JuKwPslXhMZu9/Fq97PlMzN3DPHqwDVtv7Tf+B\nvIvxU4yZvIVbhhYEVVXzkilfgQz8wRehyY5OL+fEj6ubEmblj30UOdn7iG+XkE9C\nD6a8tcZDAgMBAAECggEADAeHLv4wNad03O2C0V7G0Sd3lDGRPeJIyoSHohCC+imM\n8rJVoueXaC+pB/m+pGvLMV6guMt9AIYVOKoeuNRvcQCDSDIqvOWhHBJJmTEir9ib\nN7TWw5X+PW0c+k8EloQ4jc43s9hfRFi0iNb1xRLpLaQ5MX60FG7cuArpy+vUuNTO\ncWwIuxm5teijqBSjWL8wYC/dkFfVBP5bjtWV2n2hBguGaW0aTeXP/ZMXFdYmu+XA\n2hn8edQj8lcvVk2co+tX736ylqaL5H+PTzkDfbhXfmtyX/5QkH345lsGVJPpM7mx\nIQHWE4bmjWFOWBu5mO5twIEC/9NZ/wRkRwy0nFAUMQKBgQDhna3dKALoHP9fQW7/\nlkYWRA3KuzmV0WKMjvPLHnp7rZ9Ob+yHHgDbfM62d/o0zSCaAXwOy6wMW8ctO7Hp\nxR11hDPMw5W3d73Ho+iMwwH8243WvXKlWrLMpjKWgJazVGSbBdtG9+8vCCztOwja\nqCXQgDY3l29S8J/cZ0vJMCMqJwKBgQC7CIChYqOXkcKtB3c8WYnwGV6REMCWOH3n\nFJ13UMbX4Gg6U3wAcFgXGk3srjoRGEo9ZKnbMzxHvUubpYQ/NpCO6bQO0HJz+ZVd\n7z0U+tuXY2roqy9OujLv1r6IjQ1IJG1wy9D0ABFhN80EOYRULLYoNtF0UIgs521O\nmCuHmPQghQKBgE+c6ns94Tiy8tJULqz2BClbPlqpknpxdosO7qxgRuxZqfttt1M+\nnSLWecMdtybms3hkybgW2tFJmZE2nWNWcTzEt9qrsasaD9fg2PDC53KorvZ1x3zd\n8nYQt40wNv107ENf3669UqOQCPp1eHxgFWfrtjYLs/bafi6yLKYD3ITFAoGBAJtV\nI2dlQZKXFwSXR7rSblH+7+Aw9pGMV1w8bc7zQkF1+Xo/baP7qd1+Fiu0QzuBXd8B\nJGSNd8no98tLurq5OXPHuqf3DKcvKJM8JxGuigOAKQEVcxPCZp10TwefheFdh5r/\nWwnaqJSCQH36uuEhxSM+69PbMk2A4vI8RSK3sED1AoGBAKR73Zjkn0E6I0i9oEhB\n0vCHxWe8yQyw7ZEItp9jIu/MQrVTnun/H2WBH5wb2PHrfujcnuEI5yM5P5aVe2qM\nME36DKURgdpN9hh2K7/jA6JDj6+BD3KOuHsTFE7gJs/o+AQZWgX/zn0r47nKRPb3\nrqQ55nu90BaogDOd7So/Y0Z0\n-----END PRIVATE KEY-----\n",
                    "client_email": "firebase-adminsdk-3lgfx@mystore-eca03.iam.gserviceaccount.com",
                    "client_id": "100300151612475762258",
                    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                    "token_uri": "https://oauth2.googleapis.com/token",
                    "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-3lgfx%40mystore-eca03.iam.gserviceaccount.com",
                    "universe_domain": "googleapis.com"
            }
            """;





            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(firebaseCredentials.getBytes(StandardCharsets.UTF_8))
                    ))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully!");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Firebase initialization failed!");
        }
    }
}
