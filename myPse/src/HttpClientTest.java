import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	private static String url1 = "http://portal.inspur.com:9080/inspurportal/jsp/public/sp.jsp?j_username=C94C9E4E47EE5C65&j_password=39F635CED0FAAB8E5283D822E168125C&sqUrl=http://pse.inspur.com:9080/main.jsp?myLogin=12";
	private static String url2 = "http://pse.inspur.com:9080/workplanweek/data/WorkPlanDetail.jsp?workid=23e4eb9e3d5f443589dc40c7da94a801&canviewtitle=false&doview=true&setviewD=close&selectDate_R=2016-04-28";
    public static void main(String args[]) {
    	savePost();
    	/*
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url1);
        System.out.println(httpGet.getRequestLine());
        try {
            //执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            displayResponse(entity);
            httpGet = new HttpGet(url2);
            httpResponse = closeableHttpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            System.out.println("status:" + httpResponse.getStatusLine());
            displayResponse(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
    }
    
    public static void displayResponse(HttpEntity entity){
    	 //判断响应实体是否为空
        if (entity != null) {
            try {
            	System.out.println("contentEncoding:" + entity.getContentEncoding());
				System.out.println("response content:" + EntityUtils.toString(entity));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public static void savePost(){
    	String url = "http://pse.inspur.com:9080/workplanweek/data/WorkPlanOperation.jsp";
    	// 创建默认的httpClient实例.  
    			CloseableHttpClient httpclient = HttpClients.createDefault();
    			// 创建httppost  
    			HttpPost httppost = new HttpPost(url);
    			// 创建参数队列  
    			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();

    			formparams.add(new BasicNameValuePair("method","edit"));
    			formparams.add(new BasicNameValuePair("type_n","0"));
    			formparams.add(new BasicNameValuePair("status","0"));
    			formparams.add(new BasicNameValuePair("workid","2834f7fa1e1a446180f05e58aa719288"));
    			formparams.add(new BasicNameValuePair("selectDate","2016-05-02"));
    			formparams.add(new BasicNameValuePair("selectUser",""));
    			formparams.add(new BasicNameValuePair("viewtype",""));
    			formparams.add(new BasicNameValuePair("workPlanType",""));
    			formparams.add(new BasicNameValuePair("resremark",""));
    			formparams.add(new BasicNameValuePair("planrows","7"));
    			formparams.add(new BasicNameValuePair("planrows2","0"));
    			formparams.add(new BasicNameValuePair("planrows3","0"));
    			formparams.add(new BasicNameValuePair("pagenum","1"));
    			formparams.add(new BasicNameValuePair("name","周行动计划(5.2-5.8)-第19周"));
    			formparams.add(new BasicNameValuePair("resourceid","78620"));
    			formparams.add(new BasicNameValuePair("receiveid","67350"));
    			formparams.add(new BasicNameValuePair("shareid",""));
    			formparams.add(new BasicNameValuePair("fixedshareid","6231,65867,78514,72129,19960"));
    			formparams.add(new BasicNameValuePair("BeginDate","2016-05-02"));
    			formparams.add(new BasicNameValuePair("BeginTime",""));
    			formparams.add(new BasicNameValuePair("EndDate","2016-05-08"));
    			formparams.add(new BasicNameValuePair("EndTime",""));
    			formparams.add(new BasicNameValuePair("color","red"));
    			formparams.add(new BasicNameValuePair("crmid","0"));
    			formparams.add(new BasicNameValuePair("projectid","0"));
    			formparams.add(new BasicNameValuePair("requestid","0"));
    			formparams.add(new BasicNameValuePair("meetingid","0"));
    			formparams.add(new BasicNameValuePair("planid_0","7675334"));
    			formparams.add(new BasicNameValuePair("plandate_0","1"));
    			formparams.add(new BasicNameValuePair("morning_0","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("afternoon_0","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("night_0","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("plandoc_0",""));
    			formparams.add(new BasicNameValuePair("planid_1","7675335"));
    			formparams.add(new BasicNameValuePair("plandate_1","2"));
    			formparams.add(new BasicNameValuePair("morning_1",""));
    			formparams.add(new BasicNameValuePair("afternoon_1",""));
    			formparams.add(new BasicNameValuePair("night_1",""));
    			formparams.add(new BasicNameValuePair("plandoc_1",""));
    			formparams.add(new BasicNameValuePair("planid_2","7675336"));
    			formparams.add(new BasicNameValuePair("plandate_2","3"));
    			formparams.add(new BasicNameValuePair("morning_2",""));
    			formparams.add(new BasicNameValuePair("afternoon_2",""));
    			formparams.add(new BasicNameValuePair("night_2",""));
    			formparams.add(new BasicNameValuePair("plandoc_2",""));
    			formparams.add(new BasicNameValuePair("planid_3","7675337"));
    			formparams.add(new BasicNameValuePair("plandate_3","4"));
    			formparams.add(new BasicNameValuePair("morning_3",""));
    			formparams.add(new BasicNameValuePair("afternoon_3",""));
    			formparams.add(new BasicNameValuePair("night_3",""));
    			formparams.add(new BasicNameValuePair("plandoc_3",""));
    			formparams.add(new BasicNameValuePair("planid_4","7675338"));
    			formparams.add(new BasicNameValuePair("plandate_4","5"));
    			formparams.add(new BasicNameValuePair("morning_4","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("afternoon_4","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("night_4","(unable to decode value)"));
    			formparams.add(new BasicNameValuePair("plandoc_4",""));
    			formparams.add(new BasicNameValuePair("planid_5","7675339"));
    			formparams.add(new BasicNameValuePair("plandate_5","6"));
    			formparams.add(new BasicNameValuePair("morning_5",""));
    			formparams.add(new BasicNameValuePair("afternoon_5",""));
    			formparams.add(new BasicNameValuePair("night_5",""));
    			formparams.add(new BasicNameValuePair("plandoc_5",""));
    			formparams.add(new BasicNameValuePair("planid_6","7675340"));
    			formparams.add(new BasicNameValuePair("plandate_6","7"));
    			formparams.add(new BasicNameValuePair("morning_6",""));
    			formparams.add(new BasicNameValuePair("afternoon_6",""));
    			formparams.add(new BasicNameValuePair("night_6",""));
    			formparams.add(new BasicNameValuePair("plandoc_6",""));
    			formparams.add(new BasicNameValuePair("description",""));
    			formparams.add(new BasicNameValuePair("docid","0"));
    			formparams.add(new BasicNameValuePair("feeplanmark",""));
    			
    			UrlEncodedFormEntity uefEntity;
    			try {
    				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
    				httppost.setEntity(uefEntity);
    				System.out.println("executing request " + httppost.getURI());
    				CloseableHttpResponse response = httpclient.execute(httppost);
    				try {
    					HttpEntity entity = response.getEntity();
    					if (entity != null) {
    						System.out.println("--------------------------------------");
    						System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
    						System.out.println("--------------------------------------");
    					}
    				} finally {
    					response.close();
    				}
    			} catch (ClientProtocolException e) {
    				e.printStackTrace();
    			} catch (UnsupportedEncodingException e1) {
    				e1.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			} finally {
    				// 关闭连接,释放资源  
    				try {
    					httpclient.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    	
    }
    
}