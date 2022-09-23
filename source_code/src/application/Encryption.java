package application;

import java.security.MessageDigest;

public class Encryption {
	private static String algorithm;
	private final String salt = "fu34FInj5Dn";
	
	
	public Encryption(){
		this.algorithm = "SHA-256";
	}
	public Encryption(String algorithm){
		this.algorithm = algorithm;
	}
	
	
	
	public String getHash(String text){
		return hashing(salt + text);
	}
	
	
	
	/*@	requires base != null && !base.equals("")
	 * 	requires algorithm != null && !alcogritm.equals("")
	 * 	
	 * 	ensures !/old(base).equals(/result)
	 @*/
	private static String hashing(final String base) {
	    try{
	        final MessageDigest digest = MessageDigest.getInstance(algorithm);
	        final byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        final StringBuilder hexString = new StringBuilder();
	        for (int i = 0; i < hash.length; i++) {
	            final String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) 
	              hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
}
