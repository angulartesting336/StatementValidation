package com.assessment.springboot.statementvalidation.service;

import java.util.List;


import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;

public interface StatementValidationService {

	public ResponseDTO customerStatementValidation(List<CustomerStatementDTO> customerStatementDTOList);

}
