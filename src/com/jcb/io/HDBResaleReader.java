package com.jcb.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.jcb.bean.HDBResaleBean;
import com.jcb.bean.HDBTownBean;
import com.jcb.bean.HDBTypeBean;
import com.jcb.dao.HDBTownDAO;
import com.jcb.dao.HDBTypeDAO;

public class HDBResaleReader {

	public static List<HDBResaleBean> retrieveResales(int months) {
		List<HDBResaleBean> beans = new ArrayList<HDBResaleBean>();
		try {
			List<HDBTownBean> towns = HDBTownDAO.getHDBTowns();
			List<HDBTypeBean> hdbTypes = HDBTypeDAO.getHDBTypes();
			for (HDBTownBean town : towns) {
				for (HDBTypeBean hdbType : hdbTypes) {
					beans.addAll(retrieveResales(town.getName(), hdbType
							.getRef(), months, 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beans;
	}

	private static List<HDBResaleBean> retrieveResales(String town,
			String flatType, int startDate, int endDate) {
		List<HDBResaleBean> resales = new ArrayList<HDBResaleBean>();
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");
		BufferedReader br = null;
		PostMethod method = new PostMethod(
				"http://www.hdb.gov.sg/bb33/ispm051p.nsf/Search");
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
		method.addParameter("StartDate", String.valueOf(startDate));
		method.addParameter("%25%25Surrogate_EndDate", "1");
		method.addParameter("EndDate", String.valueOf(endDate));
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
					if (line.trim().equals("<tr class=\"svcTableRowEven\">")) {
						start = true;
					}
					if (start) {
						if (line.trim().equals("</table>")) {
							end = true;
						} else {
							HDBResaleBean bean = new HDBResaleBean();
							// System.out.println();
							String blk = getValue(br.readLine());
							String street = getValue(br.readLine());
							String story = getValue(br.readLine());
							String floorArea = getValue(br.readLine());
							String commenceDate = getValue(br.readLine());
							String price = getValue(br.readLine());
							price = StringUtils.replace(price, ",", "")
									.substring(1);
							price = price.substring(0, price.indexOf('.'));
							String approvalDate = getValue(br.readLine());
							// System.out.println(blk+" | "+street+
							// " | "+story+" | "+floorArea+" | "+
							// commenceDate+" | "+price+" | "+approvalDate);
							bean.setTown(town);
							bean.setType(flatType);
							bean.setBlk(blk);
							bean.setStreet(street);
							bean.setStory(story);
							bean.setFloorArea(Float.parseFloat(floorArea));
							bean.setLeaseCommenceDate(DateUtils.parseDate(
									commenceDate, new String[] { "yyyy" }));
							bean.setResalePrice(Integer.parseInt(price));
							SimpleDateFormat fmt = new SimpleDateFormat(
									"MMM yyyy", Locale.ENGLISH);
							bean.setResaleApprovalDate(fmt.parse(approvalDate));
							br.readLine();
							resales.add(bean);
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
				}
		}
		System.out.println("retrieveTransactions : " + town + " " + flatType
				+ " -> " + resales.size() + " records");
		return resales;
	}

	private static String getValue(String s) {
		s = s.substring(s.indexOf(">") + 1);
		s = s.substring(0, s.indexOf("<"));
		return s;
	}
}
