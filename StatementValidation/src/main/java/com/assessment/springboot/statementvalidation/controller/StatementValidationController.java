package com.assessment.springboot.statementvalidation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;
import com.assessment.springboot.statementvalidation.service.StatementValidationService;
import com.assessment.springboot.statementvalidation.service.impl.StatementValidationServiceImpl;
import com.assessment.springboot.statementvalidation.util.StatementValidationConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value = StatementValidationConstants.GLOBAL_CONTEXT_PATH)
public class StatementValidationController {
	
	@Autowired
	StatementValidationService statementValidationService;
	
	private static Logger log = LoggerFactory.getLogger(StatementValidationServiceImpl.class);
	
	@PostMapping(value = StatementValidationConstants.VALIDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = StatementValidationConstants.VALIDATE_MESSAGE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = StatementValidationConstants.API_RESPONSE_200),
			@ApiResponse(code = 400, message = StatementValidationConstants.API_RESPONSE_400),
			@ApiResponse(code = 500, message = StatementValidationConstants.API_RESPONSE_500), })
	public ResponseEntity<ResponseDTO> customerStatementValidation(@RequestBody List<CustomerStatementDTO> customerStatementDTOList) {

		return	ResponseEntity.ok().body(statementValidationService.customerStatementValidation(customerStatementDTOList));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public static final ResponseEntity<ResponseDTO> handleHttpException(HttpMessageNotReadableException ex) {
		log.error(StatementValidationConstants.PARSE_ERROR_MESSAGE, ex);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResult(StatementValidationConstants.BAD_REQUEST);
		responseDTO.setErrorRecordDTOList(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

}
