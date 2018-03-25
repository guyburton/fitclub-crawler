package com.guyburton.fitclub.generator;

import com.guyburton.fitclub.controller.UserController;
import com.guyburton.fitclub.store.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@SpringBootApplication(scanBasePackages = "com.guyburton.fitclub")
public class Generator implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Generator.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private UserController userController;

    @Value("${server.port}")
    private int port;

    private String baseDirectory = "user-pages";

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Clearing output directory");
        File file = new File(baseDirectory);
        if (file.exists()) {
            for (File child : file.listFiles()) {
                child.delete();
            }
            file.delete();
        }
        file.mkdirs();
        new File(baseDirectory, "user").mkdir();
        String baseUrl = "http://localhost:" + port;
        userRepository.findAll().forEach(user -> {
            try {
                URL url = new URL(baseUrl + "/user/" + UriUtils.encode(user.getUsername(), "UTF-8"));
                try (FileOutputStream fileOutputStream = new FileOutputStream(baseDirectory + "/user/" + user.getUsername() + ".html")) {
                    URLConnection urlConnection = url.openConnection();
                    try (InputStream inputStream = urlConnection.getInputStream()) {
                        Streams.copy(inputStream, fileOutputStream, true);
                    }
                }
            } catch (Exception e) {
                System.err.println("Could not generate user page for " + user.getUsername());
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        });

        // save down index file

        // save down all static resources

        write(getClass().getResource("/static/main.css"));
        write(new URL(baseUrl + "/users.html"));

        System.exit(0);
    }

    private void write(URL resource) throws IOException {
        String filename = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
        try (FileOutputStream fileOutputStream = new FileOutputStream(baseDirectory + "/" + filename)) {
            URLConnection urlConnection = resource.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                Streams.copy(inputStream, fileOutputStream, true);
            }
        }
    }
}
