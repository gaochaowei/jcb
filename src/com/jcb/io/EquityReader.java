package com.jcb.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jcb.bean.EquityBean;
import com.jcb.bean.EquityPriceBean;
import com.jcb.util.CommonUtils;

public class EquityReader {

	public enum Frequency {

		YEAR("y"), MONTH("m"), WEEK("w"), DATE("d");
		public String symbol;

		Frequency(String symbol) {
			this.symbol = symbol;
		}
	};

	public static List<EquityBean> fetchEquityList(String indexSymbol) {
		String url = String
				.format(
						"http://download.finance.yahoo.com/d/quotes.csv?s=@%s&f=snl1d1t1c1ohgv",
						indexSymbol);
		CSVFile csv = readCSV(url, false);
		List<EquityBean> cpv = new ArrayList<EquityBean>();
		for (String[] ss : csv.data) {
			EquityBean eq = new EquityBean();
			eq.setSymbol(ss[0]);
			eq.setName(ss[1]);
			cpv.add(eq);
		}
		return cpv;
	}

	public static List<EquityPriceBean> fetchEquityPrice(String symbol,
			Date from, Date to, Frequency freq) {
		int monthFrom = CommonUtils.getMonth(from);
		int monthTo = CommonUtils.getMonth(to);
		String url = String
				.format(
						"http://ichart.finance.yahoo.com/table.csv?s=%1$s&a=%5$d&b=%2$td&c=%2$tY&d=%6$d&e=%3$td&f=%3$tY&g=%4$s&ignore=.csv",
						symbol, from, to, freq.symbol, monthFrom, monthTo);
		CSVFile csv = readCSV(url, true);
		List<EquityPriceBean> pxs = new ArrayList<EquityPriceBean>();
		for (String[] ss : csv.data) {
			EquityPriceBean px = new EquityPriceBean();
			px.setSymbol(symbol);
			px.setDate(CommonUtils.getDate(ss[0], "yyyy-MM-dd"));
			px.setPriceOpen(Double.parseDouble(ss[1]));
			px.setPriceHigh(Double.parseDouble(ss[2]));
			px.setPriceLow(Double.parseDouble(ss[3]));
			px.setPriceClose(Double.parseDouble(ss[4]));
			px.setVolumn(Long.parseLong(ss[5]));
			px.setPriceCloseAdj(Double.parseDouble(ss[6]));
			pxs.add(0, px);
		}
		return pxs;
	}

	private static CSVFile readCSV(String url, boolean hasHeader) {
		System.out.println(url);
		CSVFile csv = new CSVFile();
		try {
			/*
			 * System.getProperties().put("proxySet", "true");
			 * System.getProperties().put("proxyPort", "8080");
			 * System.getProperties().put("proxyHost", "sinproxytd");
			 */
			URL yahoo = new URL(url);
			URLConnection yc = yahoo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc
					.getInputStream()));
			String line;
			if (hasHeader && !StringUtils.isEmpty(line = in.readLine())) {
				csv.header = StringUtils.split(line, ",");
				System.out.println("> " + line);
			}
			List<String[]> datav = new ArrayList<String[]>();
			while (!StringUtils.isEmpty(line = in.readLine())) {
				System.out.println("> " + line);
				datav.add(StringUtils.split(line, ","));
			}
			csv.data = new String[datav.size()][];
			datav.toArray(csv.data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csv;
	}
}
