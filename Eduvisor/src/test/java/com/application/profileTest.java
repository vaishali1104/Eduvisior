package com.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.application.service.PostService;
import com.application.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class profileTest {
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

	
	@Test 
	public void profileSession() throws Exception
	{ 
		mockMvc.perform(get("/profile")) 
		 	.andExpect(redirectedUrl("login"))
		  	.andDo(MockMvcResultHandlers.print()) 
			.andReturn();
	} 
	  	
	@Test public void profile() throws Exception 
	{ 
		User user=userService.getUserByMail("vmandowara11@gmail.com");	
		mockMvc.perform(get("/profile") 
				.sessionAttr("user", user))
		  .andExpect(view().name("viewprofile")) 
		  .andDo(MockMvcResultHandlers.print())
		  .andReturn(); 
	}
		  
	  
	@Test
	public void addQuestionTest() throws Exception 
	{
		mockMvc.perform(get("/addQuestion")) 
			.andExpect(redirectedUrl(""))
			//.andExpect(model().attribute("loggedinUser", new String("Please login first"))) 
			.andDo(MockMvcResultHandlers.print())
			.andReturn(); 
	} 
	  
	@Test 
	public void sessionAddQuestionTest() throws Exception 
	{ 
		User user=userService.getUserByMail("vmandowara11@gmail.com");
			mockMvc.perform(get("/addQuestion") 
					.sessionAttr("user",user))
			.andExpect(view().name("/ask_question"))
			.andDo(MockMvcResultHandlers.print()) 
			.andReturn(); 
	}
	  
	@Test 
	public void getUpdatequestion() throws Exception 
	{
		mockMvc.perform(get("/updatepost?s=5d299505abe88c1258bd7eed"))
			.andExpect(redirectedUrl("login")) 
			.andDo(MockMvcResultHandlers.print())
			.andReturn(); 
	}
	  
	@Test 
	public void sessiongetUpdatequestion() throws Exception 
	{ 
		User user=userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(get("/updatepost?s=5d299505abe88c1258bd7eed")
				.sessionAttr("user",user)) 
		.andExpect(view().name("update_post"))
		.andDo(MockMvcResultHandlers.print()) 
		.andReturn(); 
	}
	  
	@Test 
	public void downvote() throws Exception 
	{ 
		mockMvc.perform(get("/downVote?id=b70afe5d-bf67-4b18-ac22-e831ded11af2&post=5d299505abe88c1258bd7eed")) 
			.andExpect(redirectedUrl("login")) 
			.andDo(MockMvcResultHandlers.print())
			.andReturn(); 
	}
	  
	@Test 
	public void sessiondownvote() throws Exception 
	{ 
		User user=userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(get("/downVote?id=cfe56261-8a56-4f5f-a08b-6adf3235e837&post=5d1f286cff7df8368c30fb40")
				.sessionAttr("user",user)) 
			// .andExpect(view().name(""))
			.andDo(MockMvcResultHandlers.print()) 
			.andReturn(); 
	}
	  
	 
	@Test
	public void titleAddQuestion() throws Exception {
		User user = userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(post("/addQuestion").sessionAttr("user", user).contentType(MediaType.MULTIPART_FORM_DATA)
				.param("title", (String) null)).andExpect(status().isOk()).andExpect(view().name("ask_question"))
				// .andExpect(model().attributeHasFieldErrors("login", "email"))
				.andReturn();
	}

	@Test
	public void categoryAddQuestion() throws Exception {
		User user = userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(post("/addQuestion").sessionAttr("user", user).contentType(MediaType.MULTIPART_FORM_DATA)
				.param("category", (String) null)).andExpect(status().isOk()).andExpect(view().name("ask_question"))
				// .andExpect(model().attributeHasFieldErrors("login", "email"))
				.andReturn();
	}

	@Test
	public void descriptionAddQuestion() throws Exception {
		User user = userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(post("/addQuestion").sessionAttr("user", user).contentType(MediaType.MULTIPART_FORM_DATA)
				.param("description", "")).andExpect(status().isOk()).andExpect(view().name("ask_question"))
				// .andExpect(model().attributeHasFieldErrors("login", "email"))
				.andReturn();
	}

	@Test
	public void titleupdateQuestion() throws Exception {
		User user = userService.getUserByMail("mahima.teena@gmail.com");
		mockMvc.perform(post("/updatepost?s=5d299505abe88c1258bd7eed").sessionAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA).param("title", ""))
				.andExpect(redirectedUrl("updatepost?s=5d299505abe88c1258bd7eed"))
				// .andExpect(model().attributeHasFieldErrors("login", "email"))
				.andReturn();
	}

	@Test
	public void categoryupdateQuestion() throws Exception {
		User user = userService.getUserByMail("mahima.teena@gmail.com");
		mockMvc.perform(post("/updatepost?s=5d35eda5a4f7ad1db0c3058b").sessionAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA).param("category", ""))
				.andExpect(redirectedUrl("updatepost?s=5d35eda5a4f7ad1db0c3058b")).andReturn();
	}

	@Test
	public void descriptionupdateQuestion() throws Exception {
		User user = userService.getUserByMail("mahima.teena@gmail.com");
		mockMvc.perform(post("/updatepost?s=5d35eda5a4f7ad1db0c3058b").sessionAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA).param("description", ""))
				.andExpect(redirectedUrl("updatepost?s=5d35eda5a4f7ad1db0c3058b")).andReturn();
	}

	/* DELETE COMMENT */

	// Session unset
	@Test
	public void deletecomment() throws Exception {
		mockMvc.perform(
				get("/deleteComment?com_id=b70afe5d-bf67-4b18-ac22-e831ded11af2&post_id=5d299505abe88c1258bd7eed"))
				.andExpect(redirectedUrl("login")).andReturn();
	}

	@Test
	public void sessiondeletecomment() throws Exception {
		User user = userService.getUserByMail("vmandowara11@gmail.com");
		mockMvc.perform(
				get("/deleteComment?com_id=b70afe5d-bf67-4b18-ac22-e831ded11af2&post_id=5d299505abe88c1258bd7eed")
						.sessionAttr("user", user))
				.andExpect(redirectedUrl("postDetail?s=5d299505abe88c1258bd7eed")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

}
