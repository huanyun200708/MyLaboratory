import com.huawei.inoc.most.cbb.encrypt.AesEncrypt;
import com.huawei.inoc.most.cbb.encrypt.ShaEncrypt;

public class Test {
	public static void main(String[] args) {
		String encrypt = AesEncrypt.encrypt("aaa");
		System.out.println(AesEncrypt.decrypt(encrypt));
		System.out.println(ShaEncrypt.SALT);
		System.out.println(ShaEncrypt.encrypt("aaa","WL0nookvZt14sPTg"));
	}
}
