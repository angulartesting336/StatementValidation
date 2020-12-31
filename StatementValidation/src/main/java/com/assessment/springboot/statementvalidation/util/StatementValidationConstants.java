package com.assessment.springboot.statementvalidation.util;

public class StatementValidationConstants {

	public static final String GLOBAL_CONTEXT_PATH = "/statementvalidation";

	public static final String VALIDATE = "/validate";

	public static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";

	public static final String DUPLICATE_REF_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";

	public static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";

	public static final String SUCCESSFUL = "SUCCESSFUL";

	public static final String BAD_REQUEST = "BAD_REQUEST";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

	public static final String EXPECTED_JSON = "{\"result\":\"SUCCESSFUL\",\"errorRecordDTOList\":[]}";

	public static final String EXPECTED_DUPLICATE_JSON = "{\"result\":\"DUPLICATE_REFERENCE\",\"errorRecordDTOList\":[{\"reference\":1234322345,\"accountNumber\":\"NL91ABNA0417164300\"}]}";
	
	public static final String ERROR_MESSAGE = "Error during validating customer statements";
	
	public static final String PARSE_ERROR_MESSAGE = "Error while parsing the input JSON";
	
	public static final String VALIDATE_MESSAGE = "Validates monthly customer statements";
	
	public static final String API_RESPONSE_200 = "Success";
	
	public static final String API_RESPONSE_400 = "Input JSON format error";
	
	public static final String API_RESPONSE_500 = "Internal Server error";
	
	
}
