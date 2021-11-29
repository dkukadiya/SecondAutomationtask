package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MultiTest
{
    static WebDriver driver;
    public static void clickOnElement(By by)
    {
        driver.findElement(by).click();
    }
    public static void typetext(By by, String text)
    {
        driver.findElement(by).sendKeys(text);
    }
    public static String getTextFromElement(By by)
    {
        return driver.findElement(by).getText();
    }
    public static String currentTimestamp()
    {
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");
        return sdf.format(date);
    }
    public static void waitForClickable(By by,int timeInSeconds)
    {
       WebDriverWait wait= new WebDriverWait(driver,timeInSeconds);
       wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    public static void waitForVisible(By by,int timeInSeconds)
    {
        WebDriverWait wait=new WebDriverWait(driver,timeInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    @BeforeMethod
    public void openBrowser()
    {
        System.out.println(currentTimestamp());
        System.setProperty("webdriver.chrome.driver","src/test/java/driver/chromedriver.exe");
        driver =new ChromeDriver();
        driver.manage().window().maximize();
        //Type the Url
        driver.get("https://demo.nopcommerce.com/");
    }
    @Test
    public void VerifyUserShouldbeabletoRegisterSuccessfully(){
        //Click on register button
        clickOnElement(By.xpath("//a[@href='/register?returnUrl=%2F']"));
        //verify user is on register page
        Assert.assertTrue(driver.getCurrentUrl().contains(("register")));
        //Type Firstname
        typetext(By.name("FirstName"), "Viral");
        // Type last name
        typetext(By.name("LastName"), "Patel");
        //select day from dropdown
        Select selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
        selectDay.selectByVisibleText("15");
        //select month from dropdown
        Select selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectMonth.selectByValue("4");
        //Type email address
        String email = "dkukadiya+"+ currentTimestamp() +"@gmail.com";
        System.out.println(email);
        driver.findElement(By.name("Email")).sendKeys(email);
        waitForVisible(By.id("Newsletter"), 20);
        clickOnElement(By.id("Newsletter"));
        //Type Password
        typetext(By.id("Password"), "viraj123");
        //Type Confirm Password
        typetext(By.id("ConfirmPassword"), "viraj123");
        //Click on register button
        waitForClickable(By.name("register-button"), 10);
        clickOnElement(By.name("register-button"));
        //Print the Registraion completed messages
        String actualRegisterSuccessmessage = getTextFromElement(By.xpath("//div[@class='result']"));
        String expectedRegisterSuccessmessge = "Your registration completed";
        Assert.assertEquals(actualRegisterSuccessmessage,(expectedRegisterSuccessmessge),"Registration succees is Fail");
    }
    @Test
    public void VerifyUserShouldBeabletoAddNewsCommentInNewsCommentFieldAndPrint()
    {
        //Click on New online store is open
        clickOnElement(By.linkText("New online store is open!"));
        //Type the Title in Title field
        typetext(By.id("AddNewComment_CommentTitle"),"Books");
        //Type the Comment in commentfield box
        typetext(By.id("AddNewComment_CommentText"),"This Books product is really interesting and good price aswell.");
        //Click on the  new Comment button
        waitForClickable(By.xpath("//div/button[@class=\"button-1 news-item-add-comment-button\"]"),10);
        clickOnElement(By.xpath("//div/button[@class=\"button-1 news-item-add-comment-button\"]"));
        //print the 'Message News comment is successfully added' in the concole
        String ActualCommentSuccesMessage=getTextFromElement(By.xpath("//div[@class='result']"));
        String ExpectedRegisterSuccesMessage="News comment is successfully added";
    }
    @Test
    public void VerifyUsershouldBeabletoNavigateToDesktopPage()
    {
        {
            //Click on computer menu bar
            clickOnElement(By.linkText("Computers"));
            //Select the Desktops
            waitForClickable(By.linkText("Desktops"), 30);
            clickOnElement(By.linkText("Desktops"));
            //Compare the Desktops word for verify
            Assert.assertTrue(driver.getCurrentUrl().contains("desktops"));
        }
    }
    @Test
    public void VerifyregisterusershouldbeabletoReferAProductTofriend() {
        VerifyUserShouldbeabletoRegisterSuccessfully();
        //Click on computer menu bar
        clickOnElement(By.linkText("Computers"));
        //Select the Desktops
        clickOnElement(By.linkText("Desktops"));
        //click on the Build your own computer
        clickOnElement(By.linkText("Build your own computer"));
        //Click on the Email a friend Button
        clickOnElement(By.xpath("//button[@class=\"button-2 email-a-friend-button\"]"));
        //Enter the Friend's email
        typetext(By.id("FriendEmail"),"abc+" +currentTimestamp() + "@gmail.com");
        //Enter YourEmail Address
        // typetext(By.id("YourEmailAddress"),"dkukadiya+" + currentTimestamp() + "@gmail.com");
        //Enter personnel Message
        typetext(By.id("PersonalMessage"),"This product is Good and excellent condition.");
        //Click on send Email Button
        waitForClickable(By.name("send-email"), 10);
        clickOnElement(By.name("send-email"));
        //Confitmation Message on Sending Email
        String ActualMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        String ExpectedMessge = "Your message has been sent.";
        Assert.assertEquals(ActualMessage,ExpectedMessge," Your message has not been sent.");
    }
  @AfterMethod
   public void closeBrowser()
    {
       driver.close();
    }
}



