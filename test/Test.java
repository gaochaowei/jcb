import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.jcb.bean.EquityPriceBean;
import com.jcb.io.EquityReader;
import com.jcb.io.EquityReader.Frequency;
import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class Test {
	public static void main(String[] args) {
		DecimalFormat fmt = AxisUtils.getFormat(0.23);
		System.out.println(fmt.format(1203.5));
		System.out.println(AxisUtils.getUnit(1203.5));
		String[] cals = AxisUtils.getLabels(100, 2788);
		CommonUtils.printArray(cals);
		List<EquityPriceBean> pxs = EquityReader.fetchEquityPrice("c6L.SI",
				DateUtils.addDays(new Date(), -20), new Date(), Frequency.DATE);
		System.out.println(pxs);
	}
}
