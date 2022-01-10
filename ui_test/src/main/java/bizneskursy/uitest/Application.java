package bizneskursy.uitest;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/** Application which performs UI tests. */
public class Application {

    /** Url of the tested webpage. */
    private static final String baseUrl = "https://localhost:5718/";

    /** Performs all tests. */
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get(baseUrl);
        boolean result;

        result = testProductAddition(driver, 2, 10);
        System.out.println("Product addition result: " + successOrFailureString(result));

        result = testProductRemoval(driver);
        System.out.println("Product removal result: " + successOrFailureString(result));

        result = testNewAccount(driver, new AccountData("Jan", "Paciorek",
                "jan2116@example.com", "javascript", "1990-05-20"));
        System.out.println("New account result: " + successOrFailureString(result));

        result = testProductOrdering(driver);
        System.out.println("Product ordering result: " + successOrFailureString(result));

        result = testCheckOrder(driver);
        System.out.println("Order check result: " + successOrFailureString(result));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        scanner.nextLine();

        try {
            driver.close();
        }
        catch (WebDriverException exception) {
            System.out.println("Exception while closing the driver:");
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Tests adding 10 products from 2 categories.
     * @param driver web driver (webpage must be opened)
     * @return test success or failure
     */
    @SuppressWarnings("SameParameterValue")
    private static boolean testProductAddition(@NotNull WebDriver driver, int numberOfCategories, int numberOfProducts)
    {
        try {
            var rand = new Random();

            var menu = driver.findElement(By.id("category-2"));

            var categories = menu.findElements(By.className("category")).stream()
                    .map(category -> category.getAttribute("id"))
                    .collect(Collectors.toList());

            if (categories.size() < numberOfCategories) {
                System.out.println("Not enough categories!");
                return false;
            }

            var chosenCategories = categories.subList(0, numberOfCategories - 1);

            for (var category : chosenCategories) {
                try {
                    new Actions(driver).moveToElement(menu.findElement(By.className("dropdown-item"))).perform();
                    driver.findElement(By.id(category)).click();
                }
                catch (WebDriverException exception) {
                    driver.navigate().to(driver
                            .findElement(By.id(category)).findElement(By.tagName("a")).getAttribute("href"));
                }

                var products = driver.findElements(By.className("product"));

                if (products.size() < numberOfProducts / 2) {
                    System.out.println("Not enough products!");
                    return false;
                }

                List<WebElement> productsToAdd = takeRandomFrom(products, numberOfProducts);
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
                    quantity.sendKeys(String.valueOf(rand.nextInt(9)));
                    driver.findElement(By.className("add-to-cart")).click();
                    Thread.sleep(3000);
                    driver.navigate().back();
                }

                driver.navigate().back();
            }
        }
        catch (WebDriverException exception) {
            printTestExceptionInfo("product addition", exception);
            driver.navigate().to(baseUrl);
            return false;
        }
        catch(InterruptedException ex){
            System.err.println(ex.getMessage());
        }

        return true;
    }

    /**
     * Tests removing a previously added product.
     * @param driver web driver (webpage must be opened)
     * @return test success or failure
     */
    private static boolean testProductRemoval(@NotNull WebDriver driver) {
        try {
            driver.findElement(By.className("shopping-cart")).click();
            driver.findElement(By.className("remove-from-cart")).click();
            Thread.sleep(3000);
        }
        catch (WebDriverException exception) {
            printTestExceptionInfo("product removal", exception);
            driver.navigate().to(baseUrl);
            return false;
        }
        catch (InterruptedException ex){
            System.err.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Tests creating a new account.
     * @param driver web driver (webpage must be opened)
     * @param data input that would be provided by the user to create the account
     * @return test success or failure
     */
    private static boolean testNewAccount(@NotNull WebDriver driver, @NotNull AccountData data) {
        try {
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

            System.out.println("Account info:");
            System.out.println(driver.findElement(By.className("hidden-sm-down")).getText());
        }
        catch (WebDriverException exception) {
            printTestExceptionInfo("new account creation", exception);
            driver.navigate().to(baseUrl);
            return false;
        }

        return true;
    }

    /**
     * Tests ordering previously added products.
     * @param driver web driver (webpage must be opened)
     * @return test success or failure
     */
    private static boolean testProductOrdering(@NotNull WebDriver driver) {
        try {
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

            try {
                driver.findElement(By.id("delivery_option_7")).click();
            }
            catch (WebDriverException exception) {
                System.out.println("Only one delivery option!");
            }

            driver.findElements(By.className("continue")).stream()
                    .filter(e -> e.getAttribute("name").equals("confirmDeliveryOption"))
                    .collect(Collectors.toList())
                    .get(0)
                    .click();

            driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();

            try {
                driver.findElement(By.id("payment-option-2")).click();
            }
            catch (WebDriverException exception) {
                System.out.println("Only one payment option!");
            }

            driver.findElement(By.id("payment-confirmation")).findElement(By.tagName("button")).click();
        }
        catch (WebDriverException exception) {
            printTestExceptionInfo("product ordering", exception);
            driver.navigate().to(baseUrl);
            return false;
        }

        return true;
    }

    /**
     * Tests checking the order.
     * @param driver web driver (webpage must be opened)
     * @return test success or failure
     */
    private static boolean testCheckOrder(WebDriver driver) {
        try {
            Thread.sleep(5000);
            driver.findElement(By.className("account")).click();
            driver.findElement(By.id("history-link")).click();
            driver.findElement(By.className("order-actions")).findElement(By.tagName("a")).click();
            System.out.println("Order status:");
            System.out.println(driver.findElement(By.id("order-history"))
                    .findElement(By.className("label-pill")).getText());
        }
        catch (WebDriverException exception) {
            printTestExceptionInfo("checking order", exception);
            driver.navigate().to(baseUrl);
            return false;
        }
        catch (InterruptedException ex){
            System.err.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Takes random items from a list. These items are removed from the original list.
     * @param list list of elements
     * @param numberOfElements number of elements to take from the original list
     * @param <T> type of element in the list
     * @return taken items as list
     */
    @SuppressWarnings("SameParameterValue")
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

    /** Prints project-specific information about the exception.
     * @param when what test was being performed when the exception was caught
     * @param exception exception caught
     */
    private static void printTestExceptionInfo(String when, WebDriverException exception) {
        System.out.println("Exception while testing: " + when);
        exception.printStackTrace();
    }

    /**
     * Helper function for displaying result.
     * @param result the result to print
     * @return "success" or "failure" string
     */
    private static String successOrFailureString(boolean result) {
        return result ? "success" : "failure";
    }

    /** Record used to keep data necessary to create a new account. */
    private record AccountData(String firstname, String lastname, String email, String password, String birthday) {}
}
