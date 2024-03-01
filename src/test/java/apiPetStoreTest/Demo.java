package apiPetStoreTest;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class Demo {
    @Test
    void demo() {
       open("https://wiremock.org/docs/stubbing/");
       sleep(5000);
        }
}

