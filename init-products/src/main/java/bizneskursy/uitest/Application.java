package bizneskursy.uitest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        String baseUrl = "http://localhost:8080/";

        driver.get(baseUrl);

        var result = testNewAccount(driver, new AccountData("Jan", "Paciorek",
                "jan1234@example.com", "javascript", "1990-06-06"));

        System.out.println("New account result: " + result);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        scanner.nextLine();

        driver.close();
    }

    private static boolean testNewAccount(WebDriver driver, AccountData data) {
        driver.findElement(By.className("user-info")).click();
        driver.findElement(By.className("no-account")).click();
        driver.findElement(By.id("field-id_gender-1")).click();

        var firstname = driver.findElement(By.id("field-firstname"));
        firstname.click();
        firstname.sendKeys(data.firstname());

        var lastname = driver.findElement(By.id("field-lastname"));
        lastname.click();
        lastname.sendKeys(data.lastname());

        var email = driver.findElement(By.id("field-email"));
        email.click();
        email.sendKeys(data.email());

        var password = driver.findElement(By.id("field-password"));
        password.click();
        password.sendKeys(data.password());

        var birthdate = driver.findElement(By.id("field-birthday"));
        birthdate.click();
        birthdate.sendKeys(data.birthday());

        driver.findElement(By.name("customer_privacy")).click();
        driver.findElement(By.name("psgdpr")).click();
        driver.findElement(By.className("form-footer")).findElement(By.className("btn")).click();

        System.out.println(driver.findElement(By.className("hidden-sm-down")).getText());

        return driver.findElement(By.className("hidden-sm-down")).getText()
                .contains(data.firstname() + " " + data.lastname());
    }

    private record AccountData(String firstname, String lastname, String email, String password, String birthday) {}
}
