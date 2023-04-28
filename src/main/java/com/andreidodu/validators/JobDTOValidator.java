package com.andreidodu.validators;

import com.andreidodu.constants.JobConst;
import com.andreidodu.exception.ValidationException;

import java.util.Arrays;

public class JobDTOValidator {

    public static void validateJobType(Integer jobType ) throws ValidationException {
        if (!Arrays.asList(JobConst.TYPE_REQUEST, JobConst.TYPE_OFFER).contains(jobType)){
            throw new ValidationException("Invalid job type");
        }
    }
}
