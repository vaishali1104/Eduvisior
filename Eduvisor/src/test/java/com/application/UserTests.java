package com.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.application.model.User;
import com.application.service.UserService;

/**
 * .perform() to perform url (To perform get use .perform(get("/url")) and for post .perform(get("/post"))
 * .andExpect to check the values retrieved in response with the given data(Used to check actual output and expected output
 * status().isOk() is to check whether link is proper or not.. (Check if link is loaded properly or not)
 * view().name("filename") is to check whether the link returns particular file with that name or not (Check for the particular file name as return
 * andDo() will perform operation to be done after all things are true. (Usually used to print details E.G: .andDo(MockMvcResultHandlers.print())
 * .andExpect(redirectedUrl("")) used to check if the url is redirected to other url or not
 * .sessionAttr("user", user) used to attach session attribute with url (E.G.: .perform(get("/url").sessionAttr("user", user)))
 * .andExpect(model().attribute("loginError", new String("No account with this mail found.")))
 * To Check whether link contain attribute name loginError with given value or not
 * 
 * 
 * SENDING FORM DATA IN POST
 * .perform(post("/login")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.param("email", "")
		.param("password", "Ojhaharsh7@"))
 * Following code is used to send form data to post request
 * .param will accept field name along with its value
 * and contentType() refers to which type is sent to form
 * 
 * 
 * CHECKING FORM ERRORS
 * .andExpect(model().attributeHasFieldErrors("register", "name"))
 * Following line is used to check for error of particular field in form
 * attributeHasFieldErrors() accepts 2 parameters, 1st parameter is model Attribute name and 2nd attribute as field name
 * 
 * 
 * **********SPECIAL NOTE**********
 * 1. DON'T CHECK status().isOk() and view().name() functions with redirectUrl() because status().isOk() and view().name() for view operation not for redirection operation
 * 2. If you stuck at some place try to just perform url and then print the values using .andDo(MockMvcResultHandlers.print())
 * 		e.g: mockMvc.perform(get("/")).andDo(MockMvcResultHandlers.print()).andReturn();
 * 3. Always use .andReturn() at the end of every mockMvc.perform(), this will return to testcase again or else it'll perform only one test
 * 4. .andExpect() :- CHECK EXPECTED OUTPUT WITH ACTUAL OUTPUT
 * 5. .perform() :- TO PERFORM A TEST ON URL
 * 6. .andDo() :- TO DO SERVERAL TASK AFTER OR WHILE TEST IS GOING ON
 * 7. .andReturn() :- TO RETURN TO TEST AFTER EXECUTION
 * **********SPECIAL NOTE**********
 * 
 * @author User
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTests {

	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;


	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
/**
 * TESING FOR HOMEPAGE
*/	
	@Test
    public void testHomePage() throws Exception {
		mockMvc.perform(get("/index"))
                .andExpect(redirectedUrl(""))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
	
	@Test
	public void HomePage2() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
	
	//TESTCASE TO CHECK LOGGED IN USER WITH AREA OF INTERESTWISE HOMEPAGE FEED
//	@Test
//	public void HomePageWithAreaOfInterestForLoggedInUser() throws Exception {
//		User user = userService.getUserByMail("ojhaharsh7@gmail.com");
//		List<Post> list = postService.filterFunction(user.getAreaOfInterest());
//		System.out.println(list);
//		mockMvc.perform(post("/")
//				.sessionAttr("user", user)
//				)
//			.andExpect(status().isOk())
//			.andExpect(view().name("index"))
//			.andExpect(model().attribute("list", list))
//			.andDo(MockMvcResultHandlers.print())
//			.andReturn();
//	}
//
///**
// * LOGIN GET AND POST REQUEST TESTING
// * 
// */
//	//LOGIN PAGE LOAD TESTING
	@Test
	public void LoginPageLoading() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//
//	//SESSION TEST WHETHER LOGIN PAGE IS OPENED WHEN USER IS LOGGED IN OR NOT
	@Test
	public void SessionTestingForLoginPage() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(get("/login")
				.sessionAttr("user", user))
			.andExpect(redirectedUrl(""))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASE WHEN BOTH EMAIL ID AND PASSWORD ARE TRUE AND EXPECT HOMEPAGE AFTER SUCCESSFUL LOGIN
	@Test
	public void LoginPageTestWithCorrectCredentials() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharsh7@gmail.com")
				.param("password", "Ojhaharsh7@")
				)
			.andExpect(redirectedUrl(""))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASE TO FOR NOT EXISTING EMAIL ACCOUNT
//	@Test
	public void LoginPageTestWithWrongEmailAndPassword() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharshvardhan@gmail.com")
				.param("password", "Ojhaharsh7@")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attribute("loginError", new String("No account with this mail found.")))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASE TO FOR INVALID PASSWORD
//	@Test
	public void LoginPageTestWithPassword() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharsh7@gmail.com")
				.param("password", "Pjhaharsh7@")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attribute("loginError", new String("Invalid Password")))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASE TO CHECK NULL EMAIIL ID AND PASSWORD
//	@Test
	public void LoginPageWithNullEmailAndNullPassword() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "")
				.param("password", "")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attributeHasFieldErrors("login", "email"))
			.andExpect(model().attributeHasFieldErrors("login", "password"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//
//	//TESTCASE TO CHECK FOR NULL EMAIL AND NOT NULL PASSWORD
//	@Test
	public void LoginPageWithNullEmailAndNotNullPassword() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "")
				.param("password", "Ojhaharsh7@")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attributeHasFieldErrors("login", "email"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}

//	//TESTCASE TO CHECK FOR NOT NULL EMAIL AND NULL PASSWORD
//	@Test
	public void LoginPageWithNotNullEmailAndNullPassword() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharsh@gmail.com")
				.param("password", "")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attributeHasFieldErrors("login", "password"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASE TO CHECK NON VERIFIED USER
//	@Test
	public void LoginPageWithNotVerifiedUser() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharsh@gmail.com")
				.param("password", "1234Qwert")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attribute("loginError", new String("You are not verified user, so kindly check your mail.")))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
//	
//	//TESTCASES TO CHECK VALID EMAIL ADDRESS
//	@Test
	public void LoginWithValidEmail() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("plainaddress"));
		list.add(new String("#@%^%#$@#$@#.com"));
		list.add(new String("@domain.com"));
		list.add(new String("<email@domain.com>"));
		list.add(new String("email.domain.com"));
		list.add(new String("email@domain@domain.com"));
		list.add(new String(".email@domain.com"));
		list.add(new String("email.@domain.com"));
		list.add(new String("email..email@domain.com"));
		list.add(new String("あいうえお@domain.com"));
		list.add(new String("email@domain.com (Joe Smith)"));
		list.add(new String("email@domain"));
		list.add(new String("email@111.222.333.44444"));
		list.add(new String("email@domain..com"));
		for(String str : list) {
			mockMvc.perform(post("/login")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("email", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("login", "email"))
				.andReturn();
		}
	}
//	
//	//TESTCASE FOR VALID PASSWORD
	@Test
	public void LoginWithValidPassword() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/login")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("password", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("login", "password"))
				.andReturn();
		}
	}
//	
///**
// * REGISTRATION PAGE GET AND POST TESTING
// */
//	//REGISTER MAPPING TESTING
	@Test
	public void RegisterationPageLoading() throws Exception {
		mockMvc.perform(get("/register"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
	
//	//SESSION TEST WHETHER REGISTER PAGE IS OPENED WHEN USER IS LOGGED IN OR NOT
	@Test
	public void SessionTestingForRegisterPage() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(get("/register")
				.sessionAttr("user", user))
			.andExpect(redirectedUrl(""))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}

//	//TESTCASE TO CHECK VALID NAME IN REGISTERATION FORM
	@Test
	public void RegistrationFormNameFieldTesting() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("AB12"));
		list.add(new String("AB12"));
		list.add(new String("ab12"));
		list.add(new String("12Ab"));
		list.add(new String("Ab12"));
		for(String str : list) {
			mockMvc.perform(post("/register")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("name", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("register", "name"))
				.andReturn();
		}
	}
	
//	//TESTCASE TO CHECK VALID EMAIL IN REGISTERATION FORM
	@Test
	public void RegistrationFormEmailFieldTesting() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("plainaddress"));
		list.add(new String("#@%^%#$@#$@#.com"));
		list.add(new String("@domain.com"));
		list.add(new String("<email@domain.com>"));
		list.add(new String("email.domain.com"));
		list.add(new String("email@domain@domain.com"));
		list.add(new String(".email@domain.com"));
		list.add(new String("email.@domain.com"));
		list.add(new String("email..email@domain.com"));
		list.add(new String("あいうえお@domain.com"));
		list.add(new String("email@domain.com (Joe Smith)"));
		list.add(new String("email@domain"));
		list.add(new String("email@111.222.333.44444"));
		list.add(new String("email@domain..com"));
		for(String str : list) {
			mockMvc.perform(post("/register")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("email", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("register", "email"))
				.andReturn();
		}
	}
//	
//	//TESTCASE TO CHECK FOR VALID MOBILE NUMBER IN REGISTRATION FORM
	@Test
	public void RegistraionFormContactNumberValidation() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("qwerty"));
		list.add(new String("12345"));
		list.add(new String("qwertyuiops"));
		list.add(new String("12345678901"));
		list.add(new String("qwerty1234"));
		list.add(new String("1234qwerty"));
		list.add(new String("QWERTY1234"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^&*()"));
		list.add(new String("0123456789"));
		for(String str : list) {
			mockMvc.perform(post("/register")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("conactNumber", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("register", "contactNumber"))
				.andReturn();
		}
	}
//	
//	//TESTCASE TO CHECK NOT NULL PROFESSION IN REGISTRATION FORM
	@Test
	public void RegistrationFormProfessionValidation() throws Exception {
		mockMvc.perform(post("/register")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("profession", "")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attributeHasFieldErrors("register", "profession"))
			.andReturn();
	}
//	
//	//TESTCASE TO CHECK FOR NOT VALID PASSWORD FOR REGISTRATION FORM
	@Test
	public void RegistrationFormPasswordValidation() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/register")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("password", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrors("register", "password"))
				.andReturn();
		}
	}
//	
//	//TESTCASE TO CHECK WHETHER EMAIL ENTERED IS EXISTING MAIL OR NOT
	@Test
	public void RegistrationUsingExistingMailId() throws Exception {
		mockMvc.perform(post("/register")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("name", "Vardhan")
				.param("email", "ojhaharsh7@gmail.com")
				.param("contactNumber", "7623077623")
				.param("profession", "Student")
				.param("password", "Harsh7@qwe")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("login"))
			.andExpect(model().attribute("registerError", new String("Account already exist with corresponding email address.")))
			.andReturn();
	}
	
///**
// * FORGOT PASSWORD TESTING
// * @throws Exception 
// */
//	//TESTING FORGOT PASSWORD GET REQUEST WHEN USER IS LOGGED IN
	@Test
	public void ForgotPasswordWhenUserIsLoggedIn() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(get("/forgetPassword")
				.sessionAttr("user", user)
				)
			.andExpect(redirectedUrl(""))
			.andReturn();
	}
//	
//	//TESTING FORGOT PASSWORD GET REQUEST WHEN USER IS NOT LOGGED IN
	@Test
	public void ForgotPasswordWithoutLogIn() throws Exception {
		mockMvc.perform(get("/forgetPassword"))
			.andExpect(status().isOk())
			.andExpect(view().name("forget_password"))
			.andReturn();
	}
//	
//	//TESTING EMAIL FIELD FOR FORGOT PASSWORD
	@Test
	public void ForgotPasswordEmailFieldValidation() throws Exception{
		List<String> list = new ArrayList<String>();
		list.add(new String("plainaddress"));
		list.add(new String("#@%^%#$@#$@#.com"));
		list.add(new String("@domain.com"));
		list.add(new String("<email@domain.com>"));
		list.add(new String("email.domain.com"));
		list.add(new String("email@domain@domain.com"));
		list.add(new String(".email@domain.com"));
		list.add(new String("email.@domain.com"));
		list.add(new String("email..email@domain.com"));
		list.add(new String("あいうえお@domain.com"));
		list.add(new String("email@domain.com (Joe Smith)"));
		list.add(new String("email@domain"));
		list.add(new String("email@111.222.333.44444"));
		list.add(new String("email@domain..com"));
		for(String str : list) {
			mockMvc.perform(post("/forgetPassword")
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("email", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("forget_password"))
				.andExpect(model().attributeHasFieldErrors("mailObject", "email"))
				.andReturn();
		}
	}
//	
//	//TESTING EMAIL WITH NON EXISTING MAIL ACCOUNT
	@Test
	public void ForgotPasswordWithNonExistingMail() throws Exception {
		mockMvc.perform(post("/forgetPassword")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "harsh@gmail.com")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("forget_password"))
			.andExpect(model().attribute("InvalidMail", new String("We didn't find an account for that e-mail address.")))
			.andReturn();
	}
	
	//TESTING EMAIL WITH VALID EXISTING EMAIL ACCOUNT AND USER IS NOT VERIFIED
	@Test
	public void ForgotPasswordWithExistingMailAndNotVerified() throws Exception {
		mockMvc.perform(post("/forgetPassword")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "ojhaharshvardhan44@gmail.com")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("forget_password"))
			.andExpect(model().attribute("MailSuccess", new String("Reset Password Link sent to your Mail ID.")))
			.andReturn();
	}
	
	//TESTING EMAIL WITH VALID EXISTING MAIL ACCOUNT AND USER IS VERIFIED
	@Test
	public void ForgotPasswordWithExistingMailAndVerified() throws Exception {
		mockMvc.perform(post("/forgetPassword")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("email", "201812112@daiict.ac.in")
				)
			.andExpect(status().isOk())
			.andExpect(view().name("forget_password"))
			.andExpect(model().attribute("MailSuccess", new String("Reset Password Link sent to your Mail ID.")))
			.andReturn();
	}
	
	//TESTING FORGOT PASSWORD WHEN USER IS NOT LOGGED IN
	@Test
	public void ForgetPasswordWhenUserIsNotLoggedIn() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(get("/changePassword?token=" + user.getTokenID()))
			.andExpect(status().isOk())
			.andExpect(view().name("change_password"))
			.andReturn();
	}

	//TESTING FORGOT PASSWORD WHEN USER IS LOGGED IN WITH SAME USER WHO CLICK FORGET PASSWORD LINK
	@Test
	public void ForgetPasswordWhenUserIsLoggedInWithSameAccount() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(get("/changePassword?token=" + user.getTokenID())
				.sessionAttr("user", user)
				)
			.andExpect(redirectedUrl(""))
			.andReturn();
	}
	
	//TESTING FORGOT PASSWORD WHEN USER IS LOGGED IN OTHER THAN WHO CLICKED FORGET PASSWORD
	@Test
	public void ForgetPasswordWhenUserIsLoggedInWithOtherAccount() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		User user1 = userService.getUserByMail("201812112@daiict.ac.in");
		mockMvc.perform(get("/changePassword?token=" + user.getTokenID())
				.sessionAttr("user", user1)
				)
			.andExpect(redirectedUrl(""))
			.andReturn();
	}
	
	//TESTING PASSWORD FIELD OF CHANGE PASSWORD
	@Test
	public void ForgetPasswordPasswordFieldValidation() throws Exception {
		User user = userService.getUserByMail("ojhaharsh7@gmail.com");
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/changePassword?token=" + user.getTokenID())
					.param("Password", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("change_password"))
				.andExpect(model().attributeHasFieldErrors("Changepassword", "Password"))
				.andReturn();
		}
	}
	
	//TESTING CONFIRM PASSWORD FIELD OF CHANGE PASSWORD POST MAPPING
	@Test
	public void ForgetPasswordConfirmPasswordFieldValidation() throws Exception {
		User user = userService.getUserByMail("ojhaharsh7@gmail.com");
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/changePassword?token=" + user.getTokenID())
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("ConfirmPassword", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("change_password"))
				.andExpect(model().attributeHasFieldErrors("Changepassword", "ConfirmPassword"))
				.andReturn();
		}
	}
	
	//TESTING POST REQUEST FOR FORGOT PASSWORD WITH NOT MATCHING PASSWORDS
	@Test
	public void ForgotPasswordWithNotMatchingFields() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(post("/changePassword?token=" + user.getTokenID())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("Password", "Ojhaharsh7@")
				.param("ConfirmPassword", "Harshojha7@")
				)
			.andExpect(model().attribute("PasswordMismatch", new String("Password should be same in both fields.")))
			.andExpect(status().isOk())
			.andExpect(view().name("change_password"))
			.andReturn();
	}

	//TESTING POST REQUEST FOR FORGOT PASSWORD WITH MATCHING FIELDS AND LOGGED IN USER
	@Test
	public void ForgotPasswordPostMappingWithLoggedInUser() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(post("/changePassword?token=" + user.getTokenID())
				.sessionAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("Password", "Ojhaharsh7@")
				.param("ConfirmPassword", "Ojhaharsh7@")
				)
			.andExpect(redirectedUrl(""))
			.andReturn();
	}
	
	
///**
// * CHANGE PASSWORD LINK TESTING
// */
//	
	//TESTING CHANGE PASSWORD WHEN USER IS NOT LOGGED IN
	@Test
	public void ChangePasswordWhenUserIsNotLoggedIn() throws Exception {
		mockMvc.perform(get("/changepassword"))
			.andExpect(redirectedUrl("login"))
			.andReturn();
	}
	
	//TESTING CHANGE PASSWORD WHEN USER IS LOGGED IN
	@Test
	public void ChangePasswordWhenUserIsLoggedIn() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
			mockMvc.perform(get("/changepassword")
				.sessionAttr("user", user)
				)
			.andExpect(status().isOk())
			.andExpect(view().name("change_password"))
			.andReturn();
	}

	//TESTING PASSWORD FIELD OF CHANGE PASSWORD
	@Test
	public void ChangePasswordPasswordFieldValidation() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/changepassword")
					.sessionAttr("user", user)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("Password", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("change_password"))
				.andExpect(model().attributeHasFieldErrors("Changepassword", "Password"))
				.andReturn();
		}
	}
	
	//TESTING CONFIRM PASSWORD FIELD OF CHANGE PASSWORD POST MAPPING
	@Test
	public void ChangePasswordConfirmPasswordFieldValidation() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		List<String> list = new ArrayList<String>();
		list.add(new String("1234"));
		list.add(new String("12AB"));
		list.add(new String("12ab"));
		list.add(new String("QWERTY"));
		list.add(new String("qwerty"));
		list.add(new String("1234567890"));
		list.add(new String("QWERTYUIOP"));
		list.add(new String("qwertyuiop"));
		list.add(new String("qwertyUIOP"));
		list.add(new String("1234qwerty"));
		list.add(new String("1234QWERTY"));
		list.add(new String("!@#$%^"));
		list.add(new String("!@#$%^&*("));
		list.add(new String("!@#$56789"));
		list.add(new String("!@#$qwerty"));
		list.add(new String("!@#45qwerty"));
		list.add(new String("!@#$QWERTY"));
		list.add(new String("!@#45QWERTY"));
		list.add(new String("QQQWERTYUIOPasdfghjkzxcvbnm1234567890qwertyuixcvbndfghjertyu34567sdfghj"));
		list.add(new String("QWErtyuiop1234567890qwertyuiop1234567890q"));
		for(String str : list) {
			mockMvc.perform(post("/changepassword")
					.sessionAttr("user", user)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.param("ConfirmPassword", str)
					)
				.andExpect(status().isOk())
				.andExpect(view().name("change_password"))
				.andExpect(model().attributeHasFieldErrors("Changepassword", "ConfirmPassword"))
				.andReturn();
		}
	}
	
	//TESTING POST REQUEST FOR FORGOT PASSWORD WITH NOT MATCHING PASSWORDS
	@Test
	public void ChangePasswordWithNotMatchingFields() throws Exception {
		User user = userService.getUserByMail("ojhaharshvardhan44@gmail.com");
		mockMvc.perform(post("/changepassword")
				.sessionAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("Password", "Ojhaharsh7@")
				.param("ConfirmPassword", "Harshojha7@")
				)
			.andExpect(model().attribute("PasswordMismatch", new String("Password should be same in both fields.")))
			.andExpect(status().isOk())
			.andExpect(view().name("change_password"))
			.andReturn();
	}

	//TESTING POST REQUEST FOR FORGOT PASSWORD WITH MATCHING FIELDS AND LOGGED IN USER
	@Test
	public void ChangePasswordPostMappingWithoutLoggedInUser() throws Exception {
		mockMvc.perform(post("/changepassword")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("Password", "Ojhaharsh7@")
				.param("ConfirmPassword", "Ojhaharsh7@")
				)
			.andExpect(redirectedUrl("login"))
			.andReturn();
	}

}