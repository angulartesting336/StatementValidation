package com.assessment.springboot.statementvalidation.model;

import java.io.Serializable;

public class ErrorRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67641436562770970L;
	
	private int reference;
	
	private String accountNumber;

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + reference;
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
		ErrorRecordDTO other = (ErrorRecordDTO) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (reference != other.reference)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErrorRecordDTO [reference=" + reference + ", accountNumber=" + accountNumber + "]";
	}
	


}

