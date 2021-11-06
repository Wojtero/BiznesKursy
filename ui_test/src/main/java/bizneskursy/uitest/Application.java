package bizneskursy.uitest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.input.model.MouseButton;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Mouse;

import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        String baseUrl = "http://localhost:8080/";

        driver.get(baseUrl);

//        var result = testNewAccount(driver, new AccountData("Jan", "Paciorek",
//                "jan12345@example.com", "javascript", "1990-06-06"));

        var result = testProductAddition(driver);

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

    private static boolean testProductAddition(WebDriver driver) {
        var categories = driver.findElements(By.className("category")).stream()
                .map(category -> category.findElement(By.tagName("a")).getAttribute("href"))
                .collect(Collectors.toList());
        final int numberOfCategories = 2;
        var rand = new Random();

        if (categories.size() < numberOfCategories) {
            System.out.println("Not enough categories!");
            return false;
        }

        var chosenCategories = takeRandomFrom(categories, numberOfCategories);
        int numberOfProductsToAdd = 5;

        for (var category : chosenCategories) {
            driver.navigate().to(category);

            var products = driver.findElements(By.className("product"));

            if (products.size() < numberOfProductsToAdd) {
                System.out.println("Not enough products!");
                return false;
            }

            List<WebElement> productsToAdd = takeRandomFrom(products, numberOfProductsToAdd);
            List<String> productsToAddLinks = new LinkedList<>();
            for (var product : productsToAdd) {
                productsToAddLinks.add(product.findElement(By.tagName("a")).getAttribute("href"));
            }

            for (var productLink : productsToAddLinks) {
                driver.navigate().to(productLink);

                var quantity = driver.findElement(By.id("quantity_wanted"));
                quantity.click();
                quantity.clear();
                quantity.sendKeys(Keys.BACK_SPACE);
                quantity.sendKeys(String.valueOf(rand.nextInt(1, 10)));
                driver.findElement(By.className("add")).click();
                driver.navigate().back();
            }

            driver.navigate().back();
        }

        return true;
    }

    private static <T> List<T> takeRandomFrom(List<T> list, int numberOfElements) {
        var rand = new Random();
        List<T> chosen = new LinkedList<>();

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(list.size());
            chosen.add(list.get(randomIndex));
            list.remove(randomIndex);
        }

        return chosen;
    }

    private record AccountData(String firstname, String lastname, String email, String password, String birthday) {}
}
