package bizneskursy.uitest;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        String baseUrl = "https://localhost/";

        driver.get(baseUrl);

        boolean result = false;
        result = testProductAddition(driver, 2, 5);
        result = testProductRemoval(driver);
        result = testNewAccount(driver, new AccountData("Jan", "Paciorek",
                "jan13@example.com", "javascript", "1990-06-06"));
        result = testProductOrdering(driver);
        result = testCheckOrder(driver);

        System.out.println("New account result: " + result);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        scanner.nextLine();

        driver.close();
    }

    private static boolean testNewAccount(@NotNull WebDriver driver, @NotNull AccountData data) {
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

    private static boolean testProductAddition(@NotNull WebDriver driver,
        int numberOfCategories, int numberOfProductsToAdd)
    {
        var categories = driver.findElements(By.className("category")).stream()
                .map(category -> category.findElement(By.tagName("a")).getAttribute("href"))
                .collect(Collectors.toList());
        var rand = new Random(2);

        if (categories.size() < numberOfCategories) {
            System.out.println("Not enough categories!");
            return false;
        }

        var chosenCategories = takeRandomFrom(categories, numberOfCategories);

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
                driver.findElement(By.className("add-to-cart")).click();
                driver.navigate().back();
            }

            driver.navigate().back();
        }

        return true;
    }

    private static boolean testProductRemoval(@NotNull WebDriver driver) {
        driver.findElement(By.className("shopping-cart")).click();
        driver.findElement(By.className("remove-from-cart")).click();
        return true;
    }

    private static boolean testProductOrdering(@NotNull WebDriver driver) {
        driver.findElement(By.className("shopping-cart")).click();
        driver.findElement(By.className("btn-primary")).click();

        var address1 = driver.findElement(By.id("field-address1"));
        address1.click();
        address1.sendKeys("ul. Przykładowa 3B/16");

        var postcode = driver.findElement(By.id("field-postcode"));
        postcode.click();
        postcode.sendKeys("09-300");

        var city = driver.findElement(By.id("field-city"));
        city.click();
        city.sendKeys("Żuromin");

        driver.findElements(By.className("continue")).stream()
                .filter(e -> e.getAttribute("name").equals("confirm-addresses"))
                .collect(Collectors.toList())
                .get(0)
                .click();

        driver.findElements(By.className("continue")).stream()
                .filter(e -> e.getAttribute("name").equals("confirmDeliveryOption"))
                .collect(Collectors.toList())
                .get(0)
                .click();

        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();

        driver.findElement(By.id("payment-confirmation")).findElement(By.tagName("button")).click();

        return true;
    }

    private static boolean testCheckOrder(WebDriver driver) {
        driver.findElement(By.className("account")).click();
        driver.findElement(By.id("history-link")).click();
        driver.findElement(By.className("order-actions")).findElement(By.tagName("a")).click();
        return true;
    }

    private static <T> @NotNull List<T> takeRandomFrom(List<T> list, int numberOfElements) {
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
