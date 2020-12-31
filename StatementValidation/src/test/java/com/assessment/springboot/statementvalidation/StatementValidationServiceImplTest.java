package com.assessment.springboot.statementvalidation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ErrorRecordDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;
import com.assessment.springboot.statementvalidation.service.impl.StatementValidationServiceImpl;
import com.assessment.springboot.statementvalidation.util.StatementValidationConstants;

@ExtendWith(MockitoExtension.class)
public class StatementValidationServiceImplTest {

	@InjectMocks
	private StatementValidationServiceImpl statementValidationServiceImpl;

	private List<CustomerStatementDTO> customerStatementList = null;

	CustomerStatementDTO customerStatementDTO = null;

	CustomerStatementDTO customerStatementDTO1 = null;

	List<ErrorRecordDTO> errorRecordDTOList = null;
	ErrorRecordDTO errorRecordDTO = null;

	ErrorRecordDTO errorRecordDTO1 = null;

	private static Logger log = LoggerFactory.getLogger(StatementValidationServiceImplTest.class);

	@BeforeEach
	public void setUp() {
		customerStatementList = new ArrayList<CustomerStatementDTO>();
		customerStatementDTO = new CustomerStatementDTO();
		customerStatementDTO.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO.setDescription("Statement of Prince");

		customerStatementDTO1 = new CustomerStatementDTO();
		customerStatementDTO1.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO1.setDescription("Statement of Prince");

		errorRecordDTOList = new ArrayList<>();
		errorRecordDTO = new ErrorRecordDTO();
		errorRecordDTO.setAccountNumber("NL91ABNA0417164300");
		errorRecordDTO.setReference(1234322345);

		errorRecordDTO1 = new ErrorRecordDTO();
		errorRecordDTO1.setAccountNumber("NL91ABNA0417164300");
		errorRecordDTO1.setReference(1234322345);

	}

	@Test
	public void validateStatementSuccessfulTestPositiveFlow() {

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.SUCCESSFUL);
		expected.setErrorRecordDTOList(new ArrayList<>());

		customerStatementDTO.setEndBalance(1000.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setTransactionReference(1234322345);
		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual.getResult(), expected.getResult());

	}

	@Test
	public void validateStatementSuccessfulTestExceptionFlow() {

		ResponseDTO expected = new ResponseDTO();
		expected.setResult(StatementValidationConstants.INTERNAL_SERVER_ERROR);
		expected.setErrorRecordDTOList(new ArrayList<>());

		try {

			customerStatementList = null;

		} catch (Exception e) {

			log.error("Error during validating customer statements", e);
		}

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual.getResult(), expected.getResult());

	}

	@Test
	public void validateStatementDuplicateTestPositiveFlow() {

		errorRecordDTOList.add(errorRecordDTO);

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.DUPLICATE_REFERENCE);
		expected.setErrorRecordDTOList(errorRecordDTOList);

		customerStatementDTO.setEndBalance(1000.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setTransactionReference(1234322345);

		CustomerStatementDTO customerStatementDTO1 = new CustomerStatementDTO();
		customerStatementDTO1.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO1.setDescription("Statement of Prince");
		customerStatementDTO1.setEndBalance(1000.0);

		customerStatementDTO1.setMutation(500.0);

		customerStatementDTO1.setStartBalance(500.0);

		customerStatementDTO1.setTransactionReference(1234322345);
		customerStatementList.add(customerStatementDTO1);
		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual, expected);

	}

	@Test
	public void validateStatementDuplicateTestExceptionFlow() {

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.INTERNAL_SERVER_ERROR);
		expected.setErrorRecordDTOList(new ArrayList<>());

		try {
			customerStatementDTO.setTransactionReference(null);
		} catch (Exception e) {

			log.error("Error during validating customer statements", e);
		}

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setTransactionReference(1234322345);

		CustomerStatementDTO customerStatementDTO1 = new CustomerStatementDTO();
		customerStatementDTO1.setAccountNumber("NL91ABNA0417164300");
		customerStatementDTO1.setDescription("Statement of Prince");
		customerStatementDTO1.setEndBalance(1000.0);

		customerStatementDTO1.setMutation(500.0);

		customerStatementDTO1.setStartBalance(500.0);

		customerStatementDTO1.setTransactionReference(1234322345);
		customerStatementList.add(customerStatementDTO1);
		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual, expected);

	}

	@Test
	public void validateStatementInvalidBalancePositiveFlow() {

		List<ErrorRecordDTO> errorRecordDTOList = new ArrayList<>();
		ErrorRecordDTO errorRecordDTO = new ErrorRecordDTO();

		errorRecordDTO.setAccountNumber("NL91ABNA0417164300");
		errorRecordDTO.setReference(1234322345);

		errorRecordDTOList.add(errorRecordDTO);

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.INCORRECT_END_BALANCE);
		expected.setErrorRecordDTOList(errorRecordDTOList);

		customerStatementDTO.setEndBalance(1001.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setTransactionReference(1234322345);

		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual, expected);

	}

	@Test
	public void validateStatementInvalidBalanceTestExceptionFlow() {

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.INTERNAL_SERVER_ERROR);
		expected.setErrorRecordDTOList(new ArrayList<>());

		try {
			customerStatementDTO.setEndBalance(null);
		} catch (Exception e) {

			log.error("Error during validating customer statements", e);
		}

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setTransactionReference(1234322345);
		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual, expected);

	}

	@Test
	public void validateStatementDuplicateAndInvalidBalancePositiveFlow() {

		errorRecordDTOList.add(errorRecordDTO1);
		errorRecordDTOList.add(errorRecordDTO);

		ResponseDTO expected = new ResponseDTO();

		expected.setResult(StatementValidationConstants.DUPLICATE_REF_INCORRECT_END_BALANCE);
		expected.setErrorRecordDTOList(errorRecordDTOList);

		customerStatementDTO.setEndBalance(1001.0);

		customerStatementDTO.setMutation(500.0);

		customerStatementDTO.setStartBalance(500.0);

		customerStatementDTO.setTransactionReference(1234322345);

		customerStatementDTO1.setEndBalance(1000.0);

		customerStatementDTO1.setMutation(500.0);

		customerStatementDTO1.setStartBalance(500.0);

		customerStatementDTO1.setTransactionReference(1234322345);
		customerStatementList.add(customerStatementDTO1);

		customerStatementList.add(customerStatementDTO);

		ResponseDTO actual = statementValidationServiceImpl.customerStatementValidation(customerStatementList);

		assertEquals(actual, expected);

	}

}
