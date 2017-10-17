import com.huawei.inoc.most.cbb.encrypt.AesEncrypt;

public class Test {
	public static void main(String[] args) {
		String encrypt = AesEncrypt.encrypt("aaa");
		System.out.println(AesEncrypt.decrypt(encrypt));
	}
}
