package com.assessment.springboot.statementvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;
import com.assessment.springboot.statementvalidation.util.StatementValidationConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class StatementValidationApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
		
	@Autowired
	private TestRestTemplate restTemplate;
	
	private ObjectMapper objectMapper = null;

	private List<CustomerStatementDTO> customerStatementList = null;
	
	@BeforeEach
	public void before() {
		customerStatementList = new ArrayList<CustomerStatementDTO>();
		objectMapper = new ObjectMapper();
	}
	
	
	@Test
	public void validateStatementSuccessfulTest() throws Exception {
		
		CustomerStatementDTO customerStatementDTO = new CustomerStatementDTO();
		customerStatementList.add(customerStatementDTO);
		
		customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
		
		customerStatementDTO.setDescription("Statement of Prince");
		
		customerStatementDTO.setEndBalance(1000.0);
		
		customerStatementDTO.setMutation(500.0);
		
		customerStatementDTO.setStartBalance(500.0);
		
		customerStatementDTO.setTransactionReference(1234322345);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        HttpEntity<List<CustomerStatementDTO>> entity = new HttpEntity<>(customerStatementList, headers);
        
		
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE, HttpMethod.POST, entity,
				ResponseDTO.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResult()).isEqualTo(StatementValidationConstants.SUCCESSFUL);
		
		}
	

@Test
public void validateStatementDuplicateTest() throws Exception {
	
	CustomerStatementDTO customerStatementDTO = new CustomerStatementDTO();
	customerStatementList.add(customerStatementDTO);
	
	customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
	
	customerStatementDTO.setDescription("Statement of Prince");
	
	customerStatementDTO.setEndBalance(1000.0);
	
	customerStatementDTO.setMutation(500.0);
	
	customerStatementDTO.setStartBalance(500.0);
	
	customerStatementDTO.setTransactionReference(1234322345);
	
	customerStatementList.add(customerStatementDTO);
	
	HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    HttpEntity<List<CustomerStatementDTO>> entity = new HttpEntity<>(customerStatementList, headers);
    
	
	ResponseEntity<ResponseDTO> response = restTemplate.exchange(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE, HttpMethod.POST, entity,
			ResponseDTO.class);
	
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody().getResult()).isEqualTo(StatementValidationConstants.DUPLICATE_REFERENCE);
	assertThat(1).isEqualTo(response.getBody().getErrorRecordDTOList().size());
	
	}

@Test
public void validateStatementIncorrectBalanceTest() throws Exception {
	
	CustomerStatementDTO customerStatementDTO = new CustomerStatementDTO();
	customerStatementList.add(customerStatementDTO);
	
	customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
	
	customerStatementDTO.setDescription("Statement of Prince");
	
	customerStatementDTO.setEndBalance(1004.0);
	
	customerStatementDTO.setMutation(500.0);
	
	customerStatementDTO.setStartBalance(500.0);
	
	customerStatementDTO.setTransactionReference(1234322345);
	
	
	HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    HttpEntity<List<CustomerStatementDTO>> entity = new HttpEntity<>(customerStatementList, headers);
    
	
	ResponseEntity<ResponseDTO> response = restTemplate.exchange(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE, HttpMethod.POST, entity,
			ResponseDTO.class);
	
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody().getResult()).isEqualTo(StatementValidationConstants.INCORRECT_END_BALANCE);
	assertThat(1).isEqualTo(response.getBody().getErrorRecordDTOList().size());
	
	}

@Test
public void validateStatementDuplicateAndIncorrectBalanceTest() throws Exception {
	
	CustomerStatementDTO customerStatementDTO = new CustomerStatementDTO();
	customerStatementList.add(customerStatementDTO);
	
	customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
	
	customerStatementDTO.setDescription("Statement of Prince");
	
	customerStatementDTO.setEndBalance(1004.0);
	
	customerStatementDTO.setMutation(500.0);
	
	customerStatementDTO.setStartBalance(500.0);
	
	customerStatementDTO.setTransactionReference(1234322345);
	customerStatementList.add(customerStatementDTO);
	
	
	HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    HttpEntity<List<CustomerStatementDTO>> entity = new HttpEntity<>(customerStatementList, headers);
    
	
	ResponseEntity<ResponseDTO> response = restTemplate.exchange(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE, HttpMethod.POST, entity,
			ResponseDTO.class);
	
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody().getResult()).isEqualTo(StatementValidationConstants.DUPLICATE_REF_INCORRECT_END_BALANCE);
	assertThat(2).isEqualTo(response.getBody().getErrorRecordDTOList().size());
	
	}

@Test
public void validateStatementDuplicateAndIncorrectBalanceTest1() throws Exception {
	
	CustomerStatementDTO customerStatementDTO = new CustomerStatementDTO();
	customerStatementList.add(customerStatementDTO);
	
	customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
	
	customerStatementDTO.setDescription("Statement of Prince");
	
	customerStatementDTO.setEndBalance(null);
	
	customerStatementDTO.setMutation(500.0);
	
	customerStatementDTO.setStartBalance(null);
	
	customerStatementDTO.setTransactionReference(1234322345);
	
	
	HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    HttpEntity<List<CustomerStatementDTO>> entity = new HttpEntity<>(customerStatementList, headers);
    
	
	ResponseEntity<ResponseDTO> response = restTemplate.exchange(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE, HttpMethod.POST, entity,
			ResponseDTO.class);
	
	
	
	assertThat(response.getBody().getResult()).isEqualTo(StatementValidationConstants.INTERNAL_SERVER_ERROR);
	
	}



@Test
public void ParseErrorTest() throws Exception {

	String request = "endBalance : 10000.1";
		

	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE)
			.content(request).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andReturn();

	assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
	ResponseDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
	assertThat(result.getResult()).isEqualTo(StatementValidationConstants.BAD_REQUEST);
	

}
}


