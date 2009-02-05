/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcb.math;

import java.util.List;

import com.jcb.bean.EquityPriceBean;

/**
 * 
 * @author gaocw
 */
public class PriceChartMath {

	public static double[] computeSMA(List<EquityPriceBean> pxs, int n) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxs.get(j).getPriceClose();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	// todo wrong calculation
	public static double[] computeEMA(List<EquityPriceBean> pxs, int n,
			double weight) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j < i; j++) {
				count++;
				avg += pxs.get(j).getPriceClose();
			}
			if (i == 0) {
				count++;
				avg = avg + pxs.get(i).getPriceClose();
				avg = avg / count;
			} else {
				avg = (avg / count) * (1 - weight) + pxs.get(i).getPriceClose()
						* weight;
			}
			avgs[i] = avg;
		}
		return avgs;
	}

	public static double[] computeSMMA(List<EquityPriceBean> pxs, int n) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxs.get(j).getPriceClose();
			}
			avg = avg / count;
			avg = (avg * (count - 1) + pxs.get(i).getPriceClose()) / count;
			avgs[i] = avg;
		}
		return avgs;
	}

	public static double[] computeLWMA(List<EquityPriceBean> pxs, int n) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			int k = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				k++;
				count += k;
				avg += pxs.get(j).getPriceClose() * k;
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	public static double[] computeVWMA(List<EquityPriceBean> pxs, int n) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count += pxs.get(j).getVolumn();
				avg += pxs.get(j).getPriceClose() * pxs.get(j).getVolumn();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	public static double[] computeVolumeMA(List<EquityPriceBean> pxs, int n) {
		double[] avgs = new double[pxs.size()];
		for (int i = 0; i < pxs.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxs.get(j).getVolumn();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}
}
