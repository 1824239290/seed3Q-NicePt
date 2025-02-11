import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;

public class SeleniumExample {

    public static void main(String[] args) throws InterruptedException {
        // 设置WebDriver路径
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");


        // 创建ChromeOptions对象
        ChromeOptions options = new ChromeOptions();
        // 设置用户数据目录
        options.addArguments("--user-data-dir=C:/Users/15199/AppData/Local/Google/Chrome/User Data");
        options.addArguments("--incognito");

        // 初始化WebDriver
        WebDriver driver = new ChromeDriver();

        // 打开目标网页
        driver.get("https://nicept.net");

        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);
        //输入用户名、密码
        System.out.println("请输入用户名");
        String username = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        System.out.println("输入验证码");
        String imageString = scanner.nextLine();
        driver.findElement(By.name("imagestring")).sendKeys(imageString);
        driver.findElement(By.xpath("//*[@id=\"nav_block\"]/form[2]/table/tbody/tr[10]/td/input[1]")).click();
//
//        if (!driver.getCurrentUrl().equals("https://www.nicept.net/index.php")){
//            System.out.println("登录出错重新登录");
//            driver.get("https://www.nicept.net");
//        }

        Thread.sleep(2000);
        //随机生成停顿时间3-5秒
        int randomSleepTime = 3000 + (int)(Math.random() * 2000);

        // 获取登录后的Cookie
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("登录后的Cookie: " + cookies);

        System.out.println("输入开始点赞的种子id");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("要点赞多少个种子（升序）");
        int num = Integer.parseInt(scanner.nextLine());
        int logstart = 0;
        int logend = num;
        num += id;
        // 如果需要重新设置Cookie
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        driver.navigate().refresh();

        for (;id < num; id++) {
            logstart += 1;
            driver.get(STR."https://www.nicept.net/details.php?id=\{id}");
            randomSleepTime = 3000 + (int)(Math.random() * 2000);
            Thread.sleep(randomSleepTime);
            try {
                driver.findElement(By.id("saythanks")).click();
                System.out.println("种子："+id+"点赞成功");
                System.out.println("目前进度："+logstart+"/"+logend);
                randomSleepTime = 3000 + (int)(Math.random() * 2000);
                Thread.sleep(randomSleepTime);
            }catch (Exception e) {
                String text = driver.findElement(By.xpath("//*[@id=\"outer\"]/font/table/tbody/tr/td/table/tbody/tr/td")).getText();
                System.out.println("种子："+id+","+text);
                System.out.println("目前进度："+logstart+"/"+logend);
                randomSleepTime = 3000 + (int)(Math.random() * 2000);
                Thread.sleep(randomSleepTime);
                continue;
            };
        }
        System.out.println("点赞任务完成");
        // 关闭浏览器
        driver.quit();
    }
}