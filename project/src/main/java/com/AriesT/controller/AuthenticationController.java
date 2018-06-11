package com.AriesT.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}

	String clientId = "4f16520c1d7b59b65c8a";
	String clientSecret = "d3b7c7c6736545ae66d3548b23a29d34075260de";
	String accessTokenUrl = null;
	String userInfoUrl = null;
	String redirectUrl = null;
	String response_type = null;
	String code = null;

	// 提交申请code的请求
	@RequestMapping("/requestServerCode")
	public String requestServerFirst() throws Exception {
		redirectUrl = "http://localhost:8081/GithubVisualize/callback";
		accessTokenUrl = "https://github.com/login/oauth/authorize";
		response_type = "code";
		
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		String requestUrl = null;
		try {
			// 构建oauthd的请求。设置请求服务地址（accessTokenUrl）、clientId、response_type、redirectUrl
			OAuthClientRequest accessTokenRequest = OAuthClientRequest.authorizationLocation(accessTokenUrl)
					.setResponseType(response_type).setClientId(clientId).setRedirectURI(redirectUrl)
					.buildQueryMessage();
			requestUrl = accessTokenRequest.getLocationUri();
			logger.info(requestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestUrl;	//这玩意有啥用呢
	}

//	@RequestMapping("/callback")	//顺手吧access也拿了
//	public void toLogin(String code) throws OAuthProblemException {
//		logger.info("-----------客户端/callbackCode--------------------------------------------------------------------------------");
//		accessTokenUrl = "https://github.com/login/oauth/access_token";
//		redirectUrl = "http://localhost:8081/GithubVisualize/accessToken";
//
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		code = httpRequest.getParameter("code");
//		logger.info(code);
//		
//		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//		try {
//			OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation(accessTokenUrl)
//					.setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(clientId).setClientSecret(clientSecret)
//					.setCode(code).setRedirectURI(redirectUrl).buildQueryMessage();
//			// 去服务端请求access token，并返回响应
//			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
//			// 获取服务端返回过来的access token
//			String accessToken = oAuthResponse.getAccessToken();
//			// 查看access token是否过期
//			Long expiresIn = oAuthResponse.getExpiresIn();
//
//			logger.info("客户端/callbackCode方法的token：：：" + accessToken);
//		} catch (OAuthSystemException e) {
//			e.printStackTrace();
//		}
//	}
}
