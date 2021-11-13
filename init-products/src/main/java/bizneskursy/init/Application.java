package bizneskursy.init;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.input.model.MouseButton;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        String baseUrl = "https://localhost/siema";

        driver.get(baseUrl);

        var data = new AccountData("kamilpal2000@gmail.com", "Formula1");

        String CSV_path = "C:\\Users\\kamil\\Downloads\\BiznesKursy-main\\BiznesKursy-main\\scraping\\test.csv";

       testAdding(driver, data, CSV_path);

     //   System.out.println("New account result: " + result2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        scanner.nextLine();

        driver.close();
    }


    private static void testAdding(WebDriver driver, AccountData data, String path) {

        String mainWindow = driver.getWindowHandle();

       var email = driver.findElement(By.id("email"));
       email.click();
       email.sendKeys(data.email());

       var password = driver.findElement(By.id("passwd"));
       password.click();
       password.sendKeys(data.password());

        driver.findElement(By.id("submit_login")).click();

        driver.findElement(By.id("subtab-AdminAdvancedParameters")).click();

        driver.findElement(By.id("subtab-AdminImport")).click();

        var entity = new Select(driver.findElement(By.id("entity")));
        entity.selectByIndex(1);

        var file = driver.findElement(By.id("file"));
      //  file.click();

        file.sendKeys(path);

        var separator = driver.findElement(By.id("separator"));
        separator.clear();
        separator.sendKeys(",");

        var multiple = driver.findElement(By.id("multiple_value_separator"));
        multiple.clear();
        multiple.sendKeys(",");

        var radio1 = driver.findElement(By.id("truncate_1"));
        radio1.click();

        var radio2 = driver.findElement(By.id("match_ref_1"));
        radio2.click();

        driver.findElement(By.name("submitImportFile")).click();

        driver.switchTo().alert().accept();

        WebElement mm;
        mm = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("valueImportMatchs")));

        var type0 = new Select(driver.findElement(By.id("type_value[0]")));
        type0.selectByValue("name");

        var type1 = new Select(driver.findElement(By.id("type_value[1]")));
        type1.selectByValue("description_short");

        var type2 = new Select(driver.findElement(By.id("type_value[2]")));
        type2.selectByValue("description");

        var type3 = new Select(driver.findElement(By.id("type_value[3]")));
        type3.selectByValue("price_tin");

        var type4 = new Select(driver.findElement(By.id("type_value[4]")));
        type4.selectByValue("image");

        var type5 = new Select(driver.findElement(By.id("type_value[5]")));
        type5.selectByValue("id");

        driver.findElement(By.className("icon-chevron-sign-right")).click();

        var type6 = new Select(driver.findElement(By.id("type_value[6]")));
        type6.selectByValue("category");

        var type7 = new Select(driver.findElement(By.id("type_value[7]")));
        type7.selectByValue("link_rewrite");

        var type8 = new Select(driver.findElement(By.id("type_value[8]")));
        type8.selectByValue("quantity");

        var type9 = new Select(driver.findElement(By.id("type_value[9]")));
        type9.selectByValue("id_tax_rules_group");

        driver.findElement(By.id("import")).click();
    }



    private record AccountData(String email, String password) {}
}