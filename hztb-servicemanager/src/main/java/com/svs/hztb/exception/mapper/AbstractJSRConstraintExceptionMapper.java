package com.svs.hztb.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.ResponseHeader;
import com.svs.hztb.common.model.StatusCode;

public class AbstractJSRConstraintExceptionMapper {
	private final static Logger logger = LoggerFactory.getLogger(AbstractJSRConstraintExceptionMapper.class);

	protected ResponseEntity<HztbResponse> toResponse(List<Pair<String, String>> errors) {
		List<ErrorStatus> errorStatusList = new ArrayList<>();
		errors.stream().forEach(e -> errorStatusList.add(buildErrorStatus(e)));

		ResponseHeader responseHeader = new ResponseHeader("1212",
				String.valueOf(Response.Status.BAD_REQUEST.getStatusCode()), errorStatusList);

		HztbResponse response = new HztbResponse(responseHeader);

		return ResponseEntity.status(Response.Status.BAD_REQUEST.getStatusCode()).body(response);
	}

	private ErrorStatus buildErrorStatus(Pair<String, String> error) {
		String reason = error.getRight();

		String fieldName = error.getLeft();
		int pos = fieldName.indexOf(".");
		if (pos != 1 && fieldName.length() > pos) {
			fieldName = fieldName.substring(++pos);
		}

		StatusCode statusCode = null;
		String message = null;
		switch (reason) {
		case "NotNull":
			statusCode = PlatformStatusCode.MANDATORY_DOCUMENT_FIELD_MISSING;
			message = "%s - The field %s is mandatory";
			break;
		case "Size":
			statusCode = PlatformStatusCode.INVALID_FIELD_LENGTH;
			message = "%s - The field %s has an incorrect size";
			break;
		case "Min":
		case "Max":
			statusCode = PlatformStatusCode.INVALID_FIELD_VALUE;
			message = "%s - The field %s does not have the correct pattern";
			break;
		case "Pattern":
			statusCode = PlatformStatusCode.INVALID_FIELD_VALUE;
			message = "%s - The field %s does not have the correct pattern";
			break;
		case "NonEmpty":
			statusCode = PlatformStatusCode.INVALID_FIELD_VALUE;
			message = "%s - The field %s cannot be empty";
			break;
		default:
			statusCode = PlatformStatusCode.PAYLOAD_COULD_NOT_BE_PARSED;
			message = "%s - The payload could not be parsed - possible problem with field %s";
			break;

		}
		logger.error("validation error for field: {} with reason: {}", fieldName, reason);
		return new ErrorStatus(statusCode.getStatusCode(), String.format(message, statusCode.getMessage(), fieldName));
	}

}
