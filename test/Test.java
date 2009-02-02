import java.text.DecimalFormat;

import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class Test {
	public static void main(String[] args) {
		DecimalFormat fmt = AxisUtils.getFormat(0.23);
		System.out.println(fmt.format(1203.5));		
		System.out.println(AxisUtils.getUnit(1203.5));
		String[]cals = AxisUtils.getCalibration(100, 2788);
		CommonUtils.printArray(cals);
	}
}
