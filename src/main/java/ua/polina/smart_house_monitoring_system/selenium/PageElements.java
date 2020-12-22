package ua.polina.smart_house_monitoring_system.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageElements {

    public WebDriver driver;

    public PageElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "password")
    private WebElement password_input;

    @FindBy(id = "username")
    private WebElement username_input;

    @FindBy(className = "btn-primary")
    private WebElement login_button;

    @FindBy(linkText = "EN")
    private WebElement en_language_link;

    @FindBy(linkText = "RU")
    private WebElement ru_language_link;

    public void login(String username, String password) {
        username_input.sendKeys(username);
        password_input.sendKeys(password);
        login_button.click();
    }

    public void change_lang_to_en() {
        en_language_link.click();
    }

    public void change_lang_to_ru() {
        ru_language_link.click();
    }

    public boolean is_lang_displayed(){
        return ru_language_link.isDisplayed()&&en_language_link.isDisplayed();
    }

    public boolean is_fields_displayed(){
        return username_input.isDisplayed()&&password_input.isDisplayed();
    }

    public boolean is_btn_displayed(){
        return login_button.isDisplayed();
    }

//    @FindBy(className = "destroy")
//    private WebElement deleteTodo;
//
//    @FindBy(css = "label[for=toggle-all]")
//    private WebElement toggleAll;
//
//    @FindBy(className = "todo-list")
//    private WebElement todoList;
//
//    @FindBy(className = "toggle")
//    private WebElement toggleTodo;
//
//    @FindBy(className = "clear-completed")
//    private WebElement clearCompleted;
//
//    @FindBy(xpath = "//*[contains(@class,'todo-count')]//*[contains(@class,'ng-binding')]")
//    private WebElement todoCount;
//
//    @FindBy(linkText = "All")
//    private WebElement allTodos;
//
//    @FindBy(linkText = "Active")
//    private WebElement activeTodos;
//
//    @FindBy(linkText = "Completed")
//    private WebElement completedTodos;
//
//    public void createTodo(String todoName) {
//        inputTodo.sendKeys(todoName);
//        inputTodo.sendKeys(Keys.RETURN);
//    }
//
//    public void toggleAllTodos() {
//        toggleAll.click();
//    }
//
//    public WebElement getCertainTodo(String todoName) {
//        return todoList.findElement(By.xpath("//*[contains(text(),'" + todoName + "')]"));
//    }
//
//    public void deleteTodo(String todoName) {
//        getCertainTodo(todoName).click();
//        deleteTodo.click();
//    }
//
//    public void editTodo(String todoName, String newName) {
//        Actions ac = new Actions(driver);
//        ac.doubleClick(getCertainTodo(todoName))
//                .sendKeys(Keys.chord(Keys.CONTROL, "a"))
//                .sendKeys(Keys.DELETE)
//                .sendKeys(newName)
//                .sendKeys(Keys.RETURN)
//                .perform();
//    }
//
//    public int getTodoCounter() {
//        return Integer.parseInt(todoCount.getText());
//    }
//
//    public int getTodoCount() {
//        return todoList.findElements(By.tagName("li")).size();
//    }
//
//    public void toggleTodo(String todoName) {
//        getCertainTodo(todoName).click();
//        toggleTodo.click();
//    }
//
//    public void showActiveTodos() {
//        activeTodos.click();
//    }
//
//    public void showCompletedTodos() {
//        completedTodos.click();
//    }
//
//    public void deleteCompletedTodo() {
//        clearCompleted.click();
//    }
}