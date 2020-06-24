package com.mht2html2txt.allstar.util;

import java.security.Key;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 后台生成token
 * 
 * @author admin
 *
 */
@Slf4j
public class JwtUtil {
	/**
	 * 使用HS256算法和Secret:bankgl生成sign key
	 * 
	 * @return
	 */
	private static Key getKeyInstance() {
		// we'll sign our JavaWebToken with our ApiKey secret
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bankgl");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	/**
	 * 使用HS256算法和signingKey生成最终的token,claims为有效载荷
	 * 
	 * @param claims
	 * @return
	 */
	public static String createJavaWebToken(Map<String, Object> claims) {
		for (Map.Entry<String, Object> element : claims.entrySet()) {
			log.info(element.getKey() + " : " + element.getValue());
		}
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
	}

	/**
	 * 解析token,同时亦能验证token,当验证失败返回null
	 * 
	 * @param jwt
	 * @return
	 */
	public static Map<String, Object> parseJavaWebToken(String jwt) {
		Map<String, Object> jwtClaims = null;
		log.info("jwt== " + jwt);
		try {
			jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
		} catch (Exception e) {
			log.error("JavaWebToken verify failed");
			e.printStackTrace();
		}
		for (Map.Entry<String, Object> entry : jwtClaims.entrySet()) {
			log.info(entry.getKey() + " : " + entry.getValue());
		}
		return jwtClaims;
	}
}