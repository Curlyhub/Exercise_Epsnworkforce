package com.epsnworkforce.homework;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.bind.MethodArgumentNotValidException;

//import lombok.val;

import com.github.javafaker.*;
import java.util.concurrent.ThreadLocalRandom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epsnworkforce.homework.controllers.HomeController;
import com.epsnworkforce.homework.models.Customer;
import com.epsnworkforce.homework.models.Transferency;
//import com.epsnworkforce.homework.repositories.CustomerRepository;
import com.epsnworkforce.homework.services.CustomerService;

@WebMvcTest(HomeController.class)
@ActiveProfiles("test")
class HomeworkApplicationTests {

	/*
	 * We can @Autowire MockMvc because the WebApplicationContext provides an
	 * instance/bean for us
	 */
	@Autowired
	MockMvc mockMvc;

	/*
	 * Jackson mapper for Object -> JSON conversion
	 */
	@Autowired
	ObjectMapper mapper;

	/*
	 * We use @MockBean because the WebApplicationContext does not provide
	 * any @Component, @Service or @Repository beans instance/bean of this service
	 * in its context. It only loads the beans solely required for testing the
	 * controller.
	 */
	@MockBean
	CustomerService customerServiceTest;


	
	/**Basic home call test */
	@Test
	public void get_toHomeCall() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name", is("Hello World")));
	}

	/**Create and save several custumers */
	@Test
	public void post_someCustomers_Inserted() throws Exception {

		/**
		 * Here we insert 10 good formated customer
		 */
		String[] CurrencyCodes = {"BTC", "EUR", "CAD", "CUC","USD","AUD"};
		Faker faker = new Faker();
		for(int h=0;  h < 10;h++){
			int cd = ThreadLocalRandom.current().nextInt(0, 6);
			var val = 15.05 + cd*Math.exp(h*cd)+cd*Math.log(1/cd);
			var name = faker.name().firstName();
			var IsTreasury = (h % 2 ==0);
			var members = new Customer(null,name,CurrencyCodes[cd],val,IsTreasury);
			String membersJsonString = this.mapper.writeValueAsString(members);
			mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/custumer/create")
					.contentType(MediaType.APPLICATION_JSON).content(membersJsonString)).andExpect(status().isOk());
		}
		


		var membersBad = new Customer(null,"Yovani","BTCX",15.05,true);
		var membersJsonStringBad = this.mapper.writeValueAsString(membersBad);
		//Here we insert a member with bad currency code			
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/custumer/create")
						.contentType(MediaType.APPLICATION_JSON).content(membersJsonStringBad)).andExpect(status().isBadRequest());		

	}

	/**Get all custumer */
	@Test
	public void get_allCustomer() throws Exception {

		List<Customer> members = new ArrayList<>();
		// Mocking out the custumer service
		Mockito.when(customerServiceTest.getAllCustumer()).thenReturn(members);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/custumer/list").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/**Delete a custumer */
	@Test
	public void delete_someCustomer() throws Exception {

		long Id = 2;

		var serviceSpy = Mockito.spy(customerServiceTest);
		Mockito.doNothing().when(serviceSpy).deleteCustumer(Id);

		mockMvc.perform(MockMvcRequestBuilders.delete("/custumer/delete/"+Id)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(customerServiceTest, times(1)).deleteCustumer(Id);
	}

	/**Update a custumer */
	@Test
	public void put_updatesomeCustomer() throws Exception {

		var custumer = new Customer(null, "Yovani", "BTC",16.00,true);
		Long Id = (long) 1;
		Mockito.when(customerServiceTest.updateCustumer(custumer, Id)).thenReturn(custumer);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/custumer/update/"+ Id, custumer).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(custumer));

		mockMvc.perform(builder).andExpect(status().isAccepted());
	}


	/**Create and save a transferency */

	@Test
	public void post_toTransfer() throws Exception {

		Long Id = (long) 1;
		Long Idto = (long) 3;
		var transfer = new Transferency();
		transfer.setFromId(Id);
		transfer.setToId(Idto);
		transfer.setMotive("It's a simple test ");
		transfer.setStatus(false);
		
		String transferJsonString = this.mapper.writeValueAsString(transfer);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer/create")
		.contentType(MediaType.APPLICATION_JSON).content(transferJsonString)).andExpect(status().isOk());
	}


	/**Get all transferency */
	@Test
	public void get_allTransfer() throws Exception {

		List<Transferency> transfers = new ArrayList<Transferency>();

		// Mocking out the vehicle service
		Mockito.when(customerServiceTest.getAllTransferencies()).thenReturn(transfers);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1//transfers/list").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}




}
