package testPool;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.OracleCodec;

public class TestESPAPI {
	public static void main(String[] args) {
		
		OracleCodec ORACLE_CODEC = new OracleCodec();
		String dir = "dir' or '1'='1";
		String sqlString = "selct * from A where dir ='" + ESAPI.encoder().encodeForSQL(ORACLE_CODEC, dir) + "'";
		System.out.println(sqlString);

	}
}
