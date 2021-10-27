package com.djv.bodesafio.djvbox.s3.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ClassConverterBuilder {

	static ModelMapper modelMapper = new ModelMapper();

	public static <S, T> T build(S source, Class<T> targetclass) {
		T newTargetClass = modelMapper.map(source, targetclass);
		return newTargetClass;
	}

	public static <S, T> List<T> buildList(List<S> source, Class<T> targetClass) {
		return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
	}
}