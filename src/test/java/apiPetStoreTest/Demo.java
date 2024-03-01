package apiPetStoreTest;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class Demo {
    @Test
    void demo() {
       open("https://wiremock.org/docs/stubbing/");
       int a = 1;
       int b = 2;
       int result = a + b;
        System.out.println(result);
        }
    }

