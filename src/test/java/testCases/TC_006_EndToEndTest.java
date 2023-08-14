/**
 *
 */
package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.*;
import pageObjects.CheckOutPage;
import testBase.BaseClass;

public class TC_006_EndToEndTest extends BaseClass {

    @Test
    public void endToendTest() throws InterruptedException
    {
        //Soft assertions
        SoftAssert myassert=new SoftAssert();


        //Account Registration
        HomePage hp = new HomePage(driver);
        hp.clickMyAccount();
        hp.clickRegister();

        AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
        regpage.setFirstName(randomeString().toUpperCase());
        regpage.setLastName(randomeString().toUpperCase());

        String email=randomeString() + "@gmail.com";
        regpage.setEmail(email);// randomly generated the email

        regpage.setTelephone("1234567");

        regpage.setPassword("test123");
        regpage.setConfirmPassword("test123");
        regpage.setPrivacyPolicy();
        regpage.clickContinue();
        Thread.sleep(3000);

        String confmsg = regpage.getConfirmationMsg();
        System.out.println(confmsg);

        myassert.assertEquals(confmsg, "Your Account Has Been Created!"); //validation

        MyAccountPage mc=new MyAccountPage(driver);
        mc.clickLogout();
        Thread.sleep(3000);


        //Login
        hp.clickMyAccount();
        hp.clickLogin();

        LoginPage lp=new LoginPage(driver);
        lp.setEmail(email);
        lp.setPassword("test123");
        lp.clickLogin();


        System.out.println("Going to My Account Page? "+ mc.isMyAccountPageExists());
        myassert.assertEquals(mc.isMyAccountPageExists(), true); //validation


        //search & add product to cart
        hp.enterProductName("Palm Treo Pro");
        hp.clickSearch();

        SearchPage sp=new SearchPage(driver);

        if(sp.isProductExist("Palm Treo Pro"))
        {
            sp.selectProduct("Palm Treo Pro");
            sp.setQuantity("2");
            sp.addToCart();

        }
        Thread.sleep(3000);
        System.out.println("Added product to cart ? "+ sp.checkConfMsg());
        myassert.assertEquals(sp.checkConfMsg(),true);


        //Shopping cart
        ShoppingCartPage sc=new ShoppingCartPage(driver);
        sc.clickItemsToNavigateToCart();
        sc.clickViewCart();
        Thread.sleep(3000);
        String totprice=sc.getTotalPrice();
        System.out.println("total price is shopping cart: "+totprice);
        myassert.assertEquals(totprice, "$675.98");   //validation
        sc.clickOnCheckout(); //navigate to checkout page
        Thread.sleep(3000);


        //Checkout Product
        CheckOutPage ch=new CheckOutPage(driver);

        ch.setfirstName(randomeString().toUpperCase());
        Thread.sleep(1000);
        ch.setlastName(randomeString().toUpperCase());
        Thread.sleep(1000);
        ch.setaddress1("address1");
        Thread.sleep(1000);
        ch.setaddress2("address2");
        Thread.sleep(1000);
        ch.setcity("Buenos Aires");
        Thread.sleep(1000);
        ch.setpin("1425");
        Thread.sleep(1000);
        ch.setCountry("Argentina");
        Thread.sleep(1000);
        ch.setState("Buenos Aires");
        Thread.sleep(1000);
        ch.clickOnContinueAfterBillingAddress();
        Thread.sleep(1000);
        ch.clickOnContinueAfterDeliveryAddress();
        Thread.sleep(1000);
        ch.setDeliveryMethodComment("testing...");
        Thread.sleep(1000);
        ch.clickOnContinueAfterDeliveryMethod();
        Thread.sleep(1000);
        ch.selectTermsAndConditions();
        Thread.sleep(1000);
        ch.clickOnContinueAfterPaymentMethod();
        Thread.sleep(2000);

        String total_price_inOrderPage=ch.getTotalPriceBeforeConfOrder();
        System.out.println("total price in confirmed order page:"+total_price_inOrderPage);
        myassert.assertEquals(total_price_inOrderPage, "$564.98"); //validation

        //Below code works ony if you configure SMTP for emails
	/*ch.clickOnConfirmOrder();
	Thread.sleep(3000);

	boolean orderconf=ch.isOrderPlaced();
	System.out.println("Is Order Placed? "+orderconf);
	myassert.assertEquals(ch.isOrderPlaced(),true);*/

        myassert.assertAll(); //conclusion
    }

}

