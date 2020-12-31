package com.assessment.springboot.statementvalidation.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assessment.springboot.statementvalidation.model.CustomerStatementDTO;
import com.assessment.springboot.statementvalidation.model.ErrorRecordDTO;
import com.assessment.springboot.statementvalidation.model.ResponseDTO;
import com.assessment.springboot.statementvalidation.service.StatementValidationService;
import com.assessment.springboot.statementvalidation.util.StatementValidationConstants;

@Service
public class StatementValidationServiceImpl implements StatementValidationService {

	private static Logger log = LoggerFactory.getLogger(StatementValidationServiceImpl.class);

	@Override
	public ResponseDTO customerStatementValidation(List<CustomerStatementDTO> customerStatementDTOList) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Set<Integer> duplicateSet = new HashSet<>();
			List<ErrorRecordDTO> referenceDTOList = customerStatementDTOList.stream()
					.filter(customerStatement -> !duplicateSet.add(customerStatement.getTransactionReference()))
					.map(customerStatement -> {
						ErrorRecordDTO errorRecordDTO = new ErrorRecordDTO();
						errorRecordDTO.setAccountNumber(customerStatement.getAccountNumber());
						errorRecordDTO.setReference(customerStatement.getTransactionReference());

						return errorRecordDTO;
					}).collect(Collectors.toList());

				List<ErrorRecordDTO> balanceDTOList = customerStatementDTOList.stream().map(customerStatementDTO -> {
				ErrorRecordDTO errorRecordDTO = null;

				if (customerStatementDTO.getEndBalance() != (customerStatementDTO.getStartBalance()
						+ customerStatementDTO.getMutation())) {

					errorRecordDTO = new ErrorRecordDTO();
				
					errorRecordDTO.setAccountNumber(customerStatementDTO.getAccountNumber());
					errorRecordDTO.setReference(customerStatementDTO.getTransactionReference());
				}
				return errorRecordDTO;
			}).filter(Objects::nonNull ).distinct().collect(Collectors.toList());
			
			
			if (referenceDTOList.isEmpty() && balanceDTOList.isEmpty()) {
				responseDTO.setResult(StatementValidationConstants.SUCCESSFUL);
			}else if(!referenceDTOList.isEmpty() && !balanceDTOList.isEmpty()) {
				responseDTO.setResult(StatementValidationConstants.DUPLICATE_REF_INCORRECT_END_BALANCE);
			}else if(!referenceDTOList.isEmpty() && balanceDTOList.isEmpty()){
				responseDTO.setResult(StatementValidationConstants.DUPLICATE_REFERENCE);
			}else {
				responseDTO.setResult(StatementValidationConstants.INCORRECT_END_BALANCE);
			}
			
			referenceDTOList.addAll(balanceDTOList);

			responseDTO.setErrorRecordDTOList(referenceDTOList);
		} catch (Exception ex) {
			log.error(StatementValidationConstants.ERROR_MESSAGE, ex);
			responseDTO.setResult(StatementValidationConstants.INTERNAL_SERVER_ERROR);
			responseDTO.setErrorRecordDTOList(new ArrayList<>());
		}

		return responseDTO;
	}

}
