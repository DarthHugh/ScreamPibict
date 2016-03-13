package br.ifpb.monteiro.scream.authentication;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.util.ByteSource;

/**
 * 
 * @author gilvonaldo
 *
 */
public class SHA_256_SecurePassword {

	private String alg = "SHA-256";
	private int iterations = 10;
	private String delim;
	private ByteSource salt;
	private SecureRandomNumberGenerator rng;
	
	public String teste(String password){
		
		delim = Shiro1CryptFormat.TOKEN_DELIMITER;
		SimpleHash hash = new SimpleHash(alg, password, null, iterations);
		System.out.println(hash);
		
		salt = rng.nextBytes();
		
		String formatted = Shiro1CryptFormat.MCF_PREFIX + password + delim + 
				iterations + delim + salt + delim + hash.toBase64();
		
		
		//String formatted = Shiro1CryptFormat.MCF_PREFIX + alg + iterations + hash.toBase64();
		
		return formatted;
		
	}
	
	public static void main(String[] args) {
		SHA_256_SecurePassword sha = new SHA_256_SecurePassword();
		sha.teste("minhasenha");
	}
	
}
