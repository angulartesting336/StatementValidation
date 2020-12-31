package com.assessment.springboot.statementvalidation.model;

import java.io.Serializable;
import java.util.List;

public class ResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8163741663236732481L;
	
	
	private String result;
	
	private List<ErrorRecordDTO> errorRecordDTOList;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ErrorRecordDTO> getErrorRecordDTOList() {
		return errorRecordDTOList;
	}

	public void setErrorRecordDTOList(List<ErrorRecordDTO> errorRecordDTOList) {
		this.errorRecordDTOList = errorRecordDTOList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorRecordDTOList == null) ? 0 : errorRecordDTOList.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseDTO other = (ResponseDTO) obj;
		if (errorRecordDTOList == null) {
			if (other.errorRecordDTOList != null)
				return false;
		} else if (!errorRecordDTOList.equals(other.errorRecordDTOList))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseDTO [result=" + result + ", errorRecordDTOList=" + errorRecordDTOList + "]";
	}
	
	
}

