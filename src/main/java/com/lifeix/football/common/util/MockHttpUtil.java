package com.lifeix.football.common.util;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

public class MockHttpUtil {

	/**
	 * 获得Response
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月10日上午8:35:50
	 *
	 * @param mvc
	 * @param builder
	 * @param httpHeaders
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String getResult(MockMvc mvc, MockHttpServletRequestBuilder builder, HttpHeaders httpHeaders, String content) throws Exception {
		builder.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		if (httpHeaders != null) {
			builder.headers(httpHeaders);
		}
		if (!StringUtils.isEmpty(content)) {
			builder.content(content);
		}
		return getResult(mvc, builder);
	}

	/**
	 * 获得Response
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月10日上午8:35:35
	 *
	 * @param mvc
	 * @param builder
	 * @return
	 * @throws Exception
	 */
	public static String getResult(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
		ResultActions resultActions = mvc.perform(builder);
		resultActions.andDo(MockMvcResultHandlers.print());
		MvcResult result = resultActions.andReturn();
		resultActions.andExpect(status().isOk());
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		return content;
	}

}
