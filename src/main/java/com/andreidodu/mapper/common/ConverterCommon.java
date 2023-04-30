package com.andreidodu.mapper.common;

import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.Converter;
import java.util.Base64;
import java.util.Optional;

public abstract class ConverterCommon {
    public final static Converter<String, Byte[]> STRING_TO_BYTES = mappingContext ->
            ArrayUtils.toObject(Optional.ofNullable(mappingContext.getSource())
                    .map(stringImage -> Base64.getDecoder().decode(stringImage))
                    .get());

    public final static Converter<Byte[], String> BYTES_TO_BASE64_STRING = mappingContext ->
            new String(Optional.ofNullable(mappingContext.getSource())
                    .map(byteArrayImage -> Base64.getEncoder().encode(ArrayUtils.toPrimitive(byteArrayImage)))
                    .get());

    public final static Converter<Byte[], String> BYTES_TO_STRING = mappingContext ->
            new String(Optional.ofNullable(mappingContext.getSource())
                    .map(byteArrayImage -> String.valueOf(ArrayUtils.toPrimitive(byteArrayImage)))
                    .get());
}
