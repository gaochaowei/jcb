/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcb.math;

import java.util.Vector;

import com.jcb.market.data.object.Price;

/**
 * 
 * @author gaocw
 */
public class GMath {

	public static double[] computeSMA(Vector<Price> pxv, int n) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxv.get(j).getPriceClose();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	// todo wrong calculation
	public static double[] computeEMA(Vector<Price> pxv, int n, double weight) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j < i; j++) {
				count++;
				avg += pxv.get(j).getPriceClose();
			}
			if (i == 0) {
				count++;
				avg = avg + pxv.get(i).getPriceClose();
				avg = avg / count;
			} else {
				avg = (avg / count) * (1 - weight) + pxv.get(i).getPriceClose()
						* weight;
			}
			avgs[i] = avg;
		}
		return avgs;
	}

	public static double[] computeSMMA(Vector<Price> pxv, int n) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxv.get(j).getPriceClose();
			}
			avg = avg / count;
			avg = (avg * (count - 1) + pxv.get(i).getPriceClose()) / count;
			avgs[i] = avg;
		}
		return avgs;
	}

	public static double[] computeLWMA(Vector<Price> pxv, int n) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			int k = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				k++;
				count += k;
				avg += pxv.get(j).getPriceClose() * k;
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	public static double[] computeVWMA(Vector<Price> pxv, int n) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count += pxv.get(j).getVolumn();
				avg += pxv.get(j).getPriceClose() * pxv.get(j).getVolumn();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}

	public static double[] computeVolumeMA(Vector<Price> pxv, int n) {
		double[] avgs = new double[pxv.size()];
		for (int i = 0; i < pxv.size(); i++) {
			double avg = 0;
			int count = 0;
			for (int j = Math.max(0, i - n + 1); j <= i; j++) {
				count++;
				avg += pxv.get(j).getVolumn();
			}
			avgs[i] = avg / count;
		}
		return avgs;
	}
}
