package com.assessment.springboot.statementvalidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assessment.springboot.statementvalidation.controller.StatementValidationController;
import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ErrorRecordDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;
import com.assessment.springboot.statementvalidation.service.StatementValidationService;
import com.assessment.springboot.statementvalidation.util.StatementValidationConstants;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
public class StatementValidationControllerTest {

	private MockMvc mvc;

	@InjectMocks
	private StatementValidationController statementValidationController;

	@Mock
	private StatementValidationService statementValidationService;

	List<CustomerStatementDTO> customerStatementDTOList = null;

	CustomerStatementDTO customerStatementDTO = null;

	CustomerStatementDTO customerStatementDTO1 = null;

	ErrorRecordDTO errorRecordDTO = null;

	List<ErrorRecordDTO> errorRecordDTOList = null;

	ResponseDTO responseDTO = null;


	@BeforeEach
	public void setUp() {

		this.mvc = MockMvcBuilders.standaloneSetup(statementValidationController).build();

		errorRecordDTO = new ErrorRecordDTO();
		errorRecordDTO.setAccountNumber("NL91ABNA0417164300");
		errorRecordDTO.setReference(1234322345);

		errorRecordDTOList = new ArrayList<>();
		errorRecordDTOList.add(errorRecordDTO);

		customerStatementDTOList = new ArrayList<>();

		customerStatementDTO = new CustomerStatementDTO();

		customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO.setDescription("Statement of Prince");

		customerStatementDTO.setEndBalance(1000.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setTransactionReference(1234322345);

		customerStatementDTO1 = new CustomerStatementDTO();

		customerStatementDTOList.add(customerStatementDTO);

		responseDTO = new ResponseDTO();
		responseDTO.setResult(StatementValidationConstants.SUCCESSFUL);
		responseDTO.setErrorRecordDTOList(new ArrayList<>());
	}

	@Test
	public void validateStatementSuccessfullPositiveFlow() throws Exception {

		Gson gson = new Gson();

		String customerStatementListJSON = gson.toJson(customerStatementDTOList);

		when(statementValidationService.customerStatementValidation(customerStatementDTOList)).thenReturn(responseDTO);

		MockHttpServletResponse response = mvc
				.perform(post(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(customerStatementListJSON).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();

		

		assertEquals(StatementValidationConstants.EXPECTED_JSON, response.getContentAsString());
	}

	@Test
	public void validateStatementDuplicateFlow() throws Exception {

		customerStatementDTO1.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO1.setDescription("Statement of Prince");

		customerStatementDTO1.setEndBalance(1000.0);

		customerStatementDTO1.setMutation(500.0);

		customerStatementDTO1.setStartBalance(500.0);

		customerStatementDTO1.setTransactionReference(1234322345);
		customerStatementDTOList.add(customerStatementDTO1);

		Gson gson = new Gson();

		String customerStatementListJSON = gson.toJson(customerStatementDTOList);

		responseDTO.setErrorRecordDTOList(errorRecordDTOList);
		responseDTO.setResult(StatementValidationConstants.DUPLICATE_REFERENCE);

		when(statementValidationService.customerStatementValidation(customerStatementDTOList)).thenReturn(responseDTO);

		MockHttpServletResponse response = mvc
				.perform(post(StatementValidationConstants.GLOBAL_CONTEXT_PATH+StatementValidationConstants.VALIDATE).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(customerStatementListJSON).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();

		

		assertEquals(StatementValidationConstants.EXPECTED_DUPLICATE_JSON, response.getContentAsString());

	}

}
