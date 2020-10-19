package com.philips.alerttocare.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philips.alerttocare.model.Icu;
import com.philips.alerttocare.repository.IcuRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = IcuController.class)
public class IcuControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IcuController icucontroller;
	@MockBean
	private IcuRepository icurepository;

	@Test
	public void testGetAllIcus() throws Exception {

		Icu mockIcu1 = new Icu();
		mockIcu1.setLabel("#Icu1");

		Icu mockIcu2 = new Icu();
		mockIcu2.setLabel("#Icu2");

		List<Icu> icuList = new ArrayList<>();
		icuList.add(mockIcu1);
		icuList.add(mockIcu2);

		Mockito.when(icucontroller.getAllIcus()).thenReturn(icuList);
		String URI = "/api/alerttocare/icus";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(icuList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	
	@Test
	public void testGetIcutById() throws Exception {
		
		Icu mockIcu = new Icu("icu1");
		icurepository.save(mockIcu);
	
		Mockito.when(icurepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockIcu));

		String URI = "/api/alerttocare/icus/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockIcu);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isNotEqualTo(expectedJson);
	}

	@Test
	public void DeleteIcu() throws Exception {

		Icu mockIcu1 = new Icu();
		mockIcu1.setLabel("#Icu1");
		
		Mockito.when(icurepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(mockIcu1));
		mockMvc.perform((delete("/api/alerttocare/icus/{id}", 1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		

	}

	  public void testCreateIcu() throws Exception {
	       	Icu icu1 = new Icu();
	       	icu1.setLabel("#Icu1");

	       	Mockito.when(icurepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(icu1));

	        mockMvc.perform((post("/api/alerttocare/icus")).content(asJsonString(icu1))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.label", Matchers.is("#Icu1")));
	                

	    }
	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	public static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
	
	
}
