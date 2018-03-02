package com.fixture.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;

import com.fixture.security.BASE64Encoder;
import com.fixture.security.coder.DESCoder;
import com.fixture.security.coder.HmacCoder;
import com.fixture.security.coder.MDCoder;
import com.fixture.security.coder.RSACoder;
import com.fixture.security.coder.SHACoder;

/**
 * 数据加密辅助类(默认编码UTF-8)
 * 
 * @author ShenHuaJie
 * @since 2011-12-31
 */
public final class SecurityUtil {
	private SecurityUtil() {
	}

	/**
	 * 默认算法密钥
	 */
	private static final byte[] ENCRYPT_KEY = { -81, 0, 105, 7, -32, 26, -49, 88 };

	public static final String CHARSET = "UTF-8";

	/**
	 * BASE64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final byte[] decryptBASE64(String key) {
		try {
			return new BASE64Encoder().decode(key);
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	/**
	 * BASE64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final String encryptBASE64(byte[] key) {
		try {
			return new BASE64Encoder().encode(key);
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptDes(String cryptData) {
		return decryptDes(cryptData, ENCRYPT_KEY);
	}

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static final String encryptDes(String data) {
		return encryptDes(data, ENCRYPT_KEY);
	}

	/**
	 * 基于MD5算法的单向加密
	 * 
	 * @param strSrc
	 *            明文
	 * @return 返回密文
	 */
	public static final String encryptMd5(String strSrc) {
		String outString = null;
		try {
			outString = encryptBASE64(MDCoder.encodeMD5(strSrc.getBytes(CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return outString;
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptSHA(String data) {
		try {
			return encryptBASE64(SHACoder.encodeSHA256(data.getBytes(CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptHMAC(String data) {
		return encryptHMAC(data, ENCRYPT_KEY);
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptDes(String cryptData, byte[] key) {
		String decryptedData = null;
		try {
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(DESCoder.decrypt(decryptBASE64(cryptData), key));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static final String encryptDes(String data, byte[] key) {
		String encryptedData = null;
		try {
			// 加密，并把字节数组编码成字符串
			encryptedData = encryptBASE64(DESCoder.encrypt(data.getBytes(), key));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptHMAC(String data, byte[] key) {
		try {
			return encryptBASE64(HmacCoder.encodeHmacSHA512(data.getBytes(CHARSET), key));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * RSA签名
	 * 
	 * @param data
	 *            原数据
	 * @return
	 */
	public static final String signRSA(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.sign(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("签名错误，错误信息：", e);
		}
	}

	/**
	 * RSA验签
	 * 
	 * @param data
	 *            原数据
	 * @return
	 */
	public static final boolean verifyRSA(String data, String publicKey, String sign) {
		try {
			return RSACoder.verify(data.getBytes(CHARSET), decryptBASE64(publicKey), decryptBASE64(sign));
		} catch (Exception e) {
			throw new RuntimeException("验签错误，错误信息：", e);
		}
	}

	/**
	 * 数据加密，算法（RSA）
	 * 
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static final String encryptRSAPrivate(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.encryptByPrivateKey(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	/**
	 * 数据解密，算法（RSA）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptRSAPublic(String cryptData, String publicKey) {
		try {
			// 把字符串解码为字节数组，并解密
			return new String(RSACoder.decryptByPublicKey(decryptBASE64(cryptData), decryptBASE64(publicKey)));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	public static String encryptPassword(String password) {
		return encryptMd5(SecurityUtil.encryptSHA(password));
	}
	
	 /**
     * 
     * Description: 字符串异或运算
     *
     * @param strHex_X
     * @param strHex_Y
     * @return 
     * @see
     */ 
    public static byte[] xorWithKey(byte[] a, byte[] key) {  
        byte[] out = new byte[a.length];  
        for (int i = 0; i < a.length; i++) {  
            out[i] = (byte) (a[i] ^ key[i % key.length]);  
        }  
        return out;  
    }  
    
    public static String base64Encode(byte[] bytes) {  
        byte[] encodeBase64 = Base64.encodeBase64(bytes);  
        return new String(encodeBase64);  
    }  
    
    public static String xorDecode(String s, String key) {  
        return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));  
    }  
      
    public static String  base64Decode(String s) {  
        return new String(Base64.decodeBase64(s));  
    }
    
    /**
     * 
     * Description: URL加密
     *
     * @param key
     * @return 
     * @see
     */
    public static String urlEncryptDes(String key)
    {
        String res="";
        res = encryptDes(key).replace("+", "-").replace("/", "_").replace("=", "");
//        System.out.println("----"+encryptDes(key));
        return res;
    }
    /**
     * 
     * Description: URL解密
     *
     * @param key
     * @return 
     * @see
     */
    public static String urlDecryptDes(String key)
    {
        String res="";
        String trueKeyString = key.replace("-", "+").replace("_", "/");
        int leng = trueKeyString.length()%4;
        if(leng>0&&(4-leng>0))
        {
            String endString = "====";
            trueKeyString=trueKeyString+endString.substring(0, 4-leng);
        }
//        System.out.println("----"+trueKeyString);
        res = decryptDes(trueKeyString);
        return res;
    }
    
    

	public static void main(String[] args) throws Exception {
		//System.out.println(encryptPassword("123456"));
//		System.out.println(encryptPassword("abc123"));
//		System.out.println(decryptDes("zwkO+FspNny="));
//	    System.out.println(xorDecode("oBSoYww03kIBeGWxCuC0o64AfpWs","fa0690051c0307d6e47e546b937d3664"));
//		System.out.println(base64Encode(new String("http://121.42.30.94:8088/web/bindShopping").getBytes()));
//		System.out.println(base64Decode(base64Encode(new String("http://121.42.30.94:8088/web/bindShopping").getBytes())));
//		System.out.println(encryptBASE64(new String("http://121.42.30.94:8088/web/bindShopping").getBytes()));
//        System.out.println(new String(decryptBASE64(encryptBASE64(new String("http://121.42.30.94:8088/web/bindShopping").getBytes()))));
	    System.out.println("group1/M00/00/46/wKjHcloAmtKATBSWABntCCpma2M208.mp41233/1322wwwwdecscsc");
		System.out.println(urlEncryptDes("group1/M00/00/46/wKjHcloAmtKATBSWABntCCpma2M208.mp41233/1322wwwwdecscsc"));
		System.out.println(urlDecryptDes("zsmp-8_AMOxgI5RHfH4FAm4hVxaM_7-kMt6aLs5rwLuC39D-2Pn91RIT9VPkSRzTcacF0aVDTZYbX92Qo11Qt_PXwxzFJGVC"));
		/*System.out.println(encryptDes("abc123"));
		System.out.println(decryptDes("INzvw/3Qc4q="));
		System.out.println(encryptMd5("SHJR"));
		System.out.println(encryptSHA("1"));
		Map<String, Object> key = RSACoder.initKey();
		String privateKey = encryptBASE64(RSACoder.getPrivateKey(key));
		String publicKey = encryptBASE64(RSACoder.getPublicKey(key));
		System.out.println(privateKey);
		System.out.println(publicKey);
		String sign = signRSA("132", privateKey);
		System.out.println(sign);
		String encrypt = encryptRSAPrivate("132", privateKey);
		System.out.println(encrypt);
		String org = decryptRSAPublic(encrypt, publicKey);
		System.out.println(org);
		System.out.println(verifyRSA(org, publicKey, sign));*/

		// System.out.println("-------列出加密服务提供者-----");
		// Provider[] pro = Security.getProviders();
		// for (Provider p : pro) {
		// System.out.println("Provider:" + p.getName() + " - version:" +
		// p.getVersion());
		// System.out.println(p.getInfo());
		// }
		// System.out.println("");
		// System.out.println("-------列出系统支持的消息摘要算法：");
		// for (String s : Security.getAlgorithms("MessageDigest")) {
		// System.out.println(s);
		// }
		// System.out.println("-------列出系统支持的生成公钥和私钥对的算法：");
		// for (String s : Security.getAlgorithms("KeyPairGenerator")) {
		// System.out.println(s);
		// }
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("s", -1);
//		System.out.println(Integer.valueOf(m.get("s").toString()));
	}
}
