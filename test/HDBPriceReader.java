import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

public class HDBPriceReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		read("04","Queenstown");
	}

	private static String getValue(String s){
		s = s.substring(s.indexOf(">")+1);
		s = s.substring(0,s.indexOf("<"));
		return s;
	}
	private static void read(String flatType, String town) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");

		BufferedReader br = null;

		PostMethod method = new PostMethod("http://www.hdb.gov.sg/bb33/ispm051p.nsf/Search");
		method.addParameter("__Click", "0");
		method.addParameter("checkTC", "Yes");
		method.addParameter("%25%25Surrogate_FlatType", "1");
		method.addParameter("FlatType", flatType);
		method.addParameter("%25%25Surrogate_HDBTown", "1");
		method.addParameter("HDBTown", town);
		method.addParameter("%25%25Surrogate_Street", "1");
		method.addParameter("Street", "");
		method.addParameter("StartBlk", "");
		method.addParameter("EndBlk", "");
		method.addParameter("StartPrc", "");
		method.addParameter("EndPrc", "");
		method.addParameter("%25%25Surrogate_StartDate", "1");
		method.addParameter("StartDate", "4");
		method.addParameter("%25%25Surrogate_EndDate", "1");
		method.addParameter("EndDate", "1");
		try {
			int returnCode = client.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err
						.println("The Post method is not implemented by this URI");
				// still consume the response body
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(method
						.getResponseBodyAsStream()));
				boolean start = false, end = false;
				
				String line;
				while (!end && ((line = br.readLine()) != null)) {
					if(line.trim().equals("<tr class=\"svcTableRowEven\">")){
						start=true;
					}
					if(start){						
						if(line.trim().equals("</table>")){
							end = true;
						}
						else {
							System.out.println();
							System.out.println(getValue(br.readLine()));
							System.out.println(getValue(br.readLine()));
							System.out.println(getValue(br.readLine()));
							System.out.println(getValue(br.readLine()));
							System.out.println(getValue(br.readLine()));
							System.out.println(getValue(br.readLine()).substring(1));
							System.out.println(getValue(br.readLine()));
							br.readLine();
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
				}
		}
	}
}
