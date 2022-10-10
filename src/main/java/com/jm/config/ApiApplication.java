package com.jm.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Quarkus RESTful API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Example RESTful API with Quarkus",
                        url = "https://jeanpier-mendoza.vercel.app",
                        email = "jeanpiermendoza@outlook.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class ApiApplication extends Application {
}
