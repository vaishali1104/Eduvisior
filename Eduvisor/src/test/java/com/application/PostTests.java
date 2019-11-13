package com.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class PostTests {

	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	/* Testing for view post */
	
	// If session is not set
		@Test
		public void SessionUnset() throws Exception {
			mockMvc.perform(get("/view_post"))
				.andExpect(redirectedUrl("login"))
				.andReturn();
		}
	
	// Check if session set
		@Test
	    public void testviewPost() throws Exception {
			User user = userService.getUserByMail("mahima.teena@gmail.com");
			mockMvc.perform(get("/view_post")
						.sessionAttr("user", user)
						)
	                .andExpect(view().name("view_post"))
	                .andDo(MockMvcResultHandlers.print())
	                .andReturn();
	    }
	
	/* POSTDETAILS TESTING */
	
		//PostDetails Mapping(GET)
			@Test
			public void PostDetailsPageLoading() throws Exception {
				mockMvc.perform(get("/postDetail?s=5d1f286cff7df8368c30fb40"))
					.andExpect(status().isOk())
					.andExpect(view().name("postDetail"))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			}
		
		// If session is not set
			@Test
			public void SessionUnSetforPostDetails() throws Exception {
				mockMvc.perform(post("/postDetail?s=5d1f286cff7df8368c30fb40"))
					.andExpect(redirectedUrl("login"))
					.andReturn();
			}
		
		// Posting successful or not
			@Test
			public void PostSetforPostDetails() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(post("/postDetail?s=5d1f286cff7df8368c30fb40")
						.sessionAttr("user", user)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.param("comment", "hahahahahaahahahaha")
						)
					.andExpect(redirectedUrl("postDetail?s=5d1f286cff7df8368c30fb40"))
					.andReturn();
			}
	
	/* DELETE POST MAPPING */
	
	// If session is not set
			@Test
			public void SessionUnSetforDeletePost() throws Exception {
				mockMvc.perform(get("/delete?id=5d282af11c713105182376a1"))
					.andExpect(redirectedUrl("login"))
					.andReturn();
			}
		
	// If Session is set then delete

			@Test
			public void DeletePost() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(get("/delete?id=5d282af11c713105182376a1")
						.sessionAttr("user", user)
						)
					.andExpect(redirectedUrl("view_post"))
					.andReturn();
			}
	
	///* UPDATE PROFILE TESTING *////
	
		// Check for session(GET MAPPING)

		@Test
		public void SessionSetforUpdateProfile() throws Exception {
			User user = userService.getUserByMail("mahima.teena@gmail.com");
			mockMvc.perform(get("/updateProfile?data="+ user.getEmail()))
				.andExpect(redirectedUrl("login"))
				.andReturn();
		}
		
	// Session set then redirect for post(GET MAPPING)
		
		@Test
		public void SendToUpdateProfile() throws Exception {
			User user = userService.getUserByMail("mahima.teena@gmail.com");
			mockMvc.perform(get("/updateProfile?data="+ user.getEmail())
					.sessionAttr("user", user)
					)
				.andExpect(view().name("update_profile"))
				.andReturn();
		}
	
			// check for session(POST MAPPING)
	
			@Test
			public void SessionSetforUpdateProfilePostMapping() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(post("/updateProfile?data="+ user.getEmail()))
					.andExpect(redirectedUrl("login"))
					.andReturn();
			}
		
			// Check for if username is empty
			
			@Test
			public void UpdateProfileNameEmpty() throws Exception {
					User user = userService.getUserByMail("mahima.teena@gmail.com");
					mockMvc.perform(post("/updateProfile?data="+ user.getEmail())
							.sessionAttr("user", user)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.param("name", "")
							.param("dob", "1996-09-13")
							.param("gender", "female")
							.param("contactNumber", "7418529630")
							.param("university", "Backwas")
							.param("degree", "MSc")
							)
						.andExpect(redirectedUrl("updateProfile?data="+ user.getEmail()))
						.andReturn();
			}
	
			// Check for if gender is empty

				@Test
				public void UpdateGenderEmpty() throws Exception {
						User user = userService.getUserByMail("mahima.teena@gmail.com");
						mockMvc.perform(post("/updateProfile?data="+ user.getEmail())
								.sessionAttr("user", user)
								.contentType(MediaType.MULTIPART_FORM_DATA)
								.param("name", "Mahima")
								.param("dob", "1997-09-28")
								.param("gender", "")
								.param("contactNumber", "7418529630")
								.param("university", "Backwas")
								.param("degree", "MSc")
								)
							.andExpect(redirectedUrl("updateProfile?data="+ user.getEmail()))
							.andReturn();
				}
			
				
				// Check if everything is ready
				
				@Test
				public void UpdateProfile() throws Exception {
						User user = userService.getUserByMail("mahima.teena@gmail.com");
						mockMvc.perform(post("/updateProfile?data="+ user.getEmail())
								.sessionAttr("user", user)
								.contentType(MediaType.MULTIPART_FORM_DATA)
								.param("name", "Mahima Baldha")
								.param("dob", "1997-09-28")
								.param("gender", "female")
								.param("contactNumber", "7418529630")
								.param("university", "Backwas")
								.param("degree", "MSc")
								)
							.andExpect(redirectedUrl("profile"))
							.andReturn();
				}
				
	
	/* Area OF Interest */
	
			// If session is not set (GET MAPPING)
	
			@Test
			public void SessionUnsetForInterest() throws Exception {
				mockMvc.perform(get("/interest"))
					.andExpect(redirectedUrl(""))
					.andReturn();
			}
			
		// If user is loggedin 
	
			@Test
			public void GetInterests() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(get("/interest")
						.sessionAttr("user", user)
						)
					.andExpect(status().isOk())
					.andExpect(view().name("interests"))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();	
			}
	
			@Test
			public void SessionUnsetForUpdateInterest() throws Exception {
				mockMvc.perform(post("/updateInterest"))
					.andExpect(redirectedUrl(""))
					.andReturn();
			}
			
			@Test
			public void UpdateInterests() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				String arr[] = new String [3];
				arr[0] = "geology";
				arr[0] = "Microbiology";
				arr[0] = "Hindi";
				mockMvc.perform(post("/updateInterest")
						.sessionAttr("user", user)
						.param("areaOfInterest", "arr")
						)
					.andExpect(redirectedUrl(""))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();	
			}
			
	/* UPVOTE MAPPING */
	
		// Check for user if loggedin
	
			@Test
			public void SessionUnsetForUpvotes() throws Exception {
				mockMvc.perform(get("/upVote?id=da138592-351b-4602-a9f9-75c6b76319fc&post=5d35eda5a4f7ad1db0c3058b"))
					.andExpect(redirectedUrl("login"))
					.andReturn();
			}
	
		// If owner of the comment tries to upvote then
	
			@Test
			public void UpvoteByOwner() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(get("/upVote?id=da138592-351b-4602-a9f9-75c6b76319fc&post=5d35eda5a4f7ad1db0c3058b")
						.sessionAttr("user", user)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.param("email", user.getEmail())
						)
					.andExpect(redirectedUrl("postDetail?s=5d35eda5a4f7ad1db0c3058b"))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();	
			}
	
			@Test
			public void UpvoteByUser() throws Exception {
				User user = userService.getUserByMail("mahima.teena@gmail.com");
				mockMvc.perform(get("/upVote?id=da138592-351b-4602-a9f9-75c6b76319fc&post=5d35eda5a4f7ad1db0c3058b")
						.sessionAttr("user", user)
						)
					.andExpect(redirectedUrl("postDetail?s=5d35eda5a4f7ad1db0c3058b"))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();	
			}

	
}
