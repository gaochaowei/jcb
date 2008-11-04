package com.jcb.market.data.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.jcb.market.data.object.IndexComponent;
import com.jcb.market.data.object.IndexComponentPK;
import com.jcb.market.data.object.Price;
import com.jcb.market.data.object.PricePK;
import com.jcb.persistence.io.CSVFile;
import com.jcb.util.CommonUtils;

public class MarketReader {

	public enum Frequency {

		YEAR("y"), MONTH("m"), WEEK("w"), DATE("d");
		public String symbol;

		Frequency(String symbol) {
			this.symbol = symbol;
		}
	};

	public static void main(String[] args) {
		Date from = CommonUtils.getDate("08/01/2008");
		Date to = CommonUtils.getDate("20/04/2008");
		System.out.println(from);
		System.out.println(to);
		readPrice("C6L.SI", from, to, Frequency.DATE);
	}

	public static CSVFile readComponentsAsCSV(String indexSymbol) {
		// String url =
		// String.format("http://download.finance.yahoo.com/d/quotes.csv?s=@%s&f=sl1d1t1c1ohgv&e=.csv&h=0",
		// indexSymbol);
		String url = String.format(
				"http://download.finance.yahoo.com/d/quotes.csv?s=@%s&f=vkjm3",
				indexSymbol);
		CSVFile csv = readCSV(url, false);
		return csv;
	}

	public static Vector<IndexComponent> readComponents(String indexSymbol) {
		String url = String
				.format(
						"http://download.finance.yahoo.com/d/quotes.csv?s=@%s&f=sl1d1t1c1ohgv&e=.csv&h=0",
						indexSymbol);
		CSVFile csv = readCSV(url, false);
		Vector<IndexComponent> cpv = new Vector<IndexComponent>();
		for (String[] ss : csv.data) {
			IndexComponent cp = new IndexComponent();
			IndexComponentPK pk = new IndexComponentPK();
			pk.setIndex(indexSymbol);
			pk.setStock(ss[0]);
			cp.setTblIndexComponentPK(pk);
			cpv.add(cp);
		}
		return cpv;
	}

	public static Vector<Price> readPrice(String symbol, Date from, Date to,
			Frequency freq) {
		int monthFrom = from.getMonth();
		int monthTo = to.getMonth();
		String url = String
				.format(
						"http://ichart.finance.yahoo.com/table.csv?s=%1$s&a=%5$d&b=%2$td&c=%2$tY&d=%6$d&e=%3$td&f=%3$tY&g=%4$s&ignore=.csv",
						symbol, from, to, freq.symbol, monthFrom, monthTo);
		CSVFile csv = readCSV(url, true);
		Vector<Price> pxv = new Vector<Price>();
		for (String[] ss : csv.data) {
			Price px = new Price();
			PricePK pk = new PricePK();
			pk.setSymbol(symbol);
			pk.setDate(CommonUtils.getDate(ss[0], "yyyy-MM-dd"));
			px.setTblPricePK(pk);
			px.setPriceOpen(Double.parseDouble(ss[1]));
			px.setPriceHigh(Double.parseDouble(ss[2]));
			px.setPriceLow(Double.parseDouble(ss[3]));
			px.setPriceClose(Double.parseDouble(ss[4]));
			px.setVolumn(Long.parseLong(ss[5]));
			px.setPriceCloseAdj(Double.parseDouble(ss[6]));
			pxv.insertElementAt(px, 0);
		}
		return pxv;
	}

	public static CSVFile readCSV(String url, boolean hasHeader) {
		System.out.println(url);
		CSVFile csv = new CSVFile();
		try {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyPort", "8080");
			System.getProperties().put("proxyHost", "sinproxytd");
			URL yahoo = new URL(url);
			URLConnection yc = yahoo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc
					.getInputStream()));
			String inputLine;
			if (hasHeader && (inputLine = in.readLine()) != null) {
				csv.header = StringUtils.split(inputLine, ",");
				System.out.println(inputLine);
			}
			List<String[]> datav = new ArrayList<String[]>();
			while ((inputLine = in.readLine()) != null) {
				datav.add(StringUtils.split(inputLine, ","));
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
