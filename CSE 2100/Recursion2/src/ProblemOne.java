

import java.util.HashSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ProblemOne extends ApplicationFrame {
	
	public ProblemOne(String title) {
		super(title);
		final XYSeries series = new XYSeries("First Method");
		final XYSeries series2 = new XYSeries("First Method Optimized");
		for (int i = 1; i < 10001; i++) {
			series.add(i, FirstMethod(i, 1));
			series2.add(i, FirstMethodSol(i, 1));
		}
		final XYSeriesCollection data = new XYSeriesCollection();
		data.addSeries(series);
		data.addSeries(series2);
		final JFreeChart chart = ChartFactory.createScatterPlot("First Result", "X", "Y", data, PlotOrientation.VERTICAL, true, true, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1600, 960));
		setContentPane(chartPanel);
		
	}
	
	public ProblemOne(String n3Problem, int yes) {
		super(n3Problem);
		final XYSeries n3V1 = new XYSeries("3n + 1 Method");
		final XYSeries n3V2 = new XYSeries("3n + 1 Optimized");
		
		for (int i = 1; i < 1000; i++) {
			n3V1.add(i, SecMeth(i, 1));
			n3V2.add(i, SecMethOpt(i, 1));
		}
		final XYSeriesCollection data2 = new XYSeriesCollection();
		data2.addSeries(n3V1);
		data2.addSeries(n3V2);
		final JFreeChart chart2 = ChartFactory.createScatterPlot("First Result", "X", "Y", data2, PlotOrientation.VERTICAL, true, true, false);
		final ChartPanel chartPanel2 = new ChartPanel(chart2);
		chartPanel2.setPreferredSize(new java.awt.Dimension(1600, 960));
		setContentPane(chartPanel2);
	}
	
	public int FirstMethod(int n, int count) {
		if (n > 0) {
			if (n == 1) {
				return 1;
			} else if (n % 2 == 0) {
				return 1 + FirstMethod(n / 2, count + 1);
			} else if (n % 2 != 0) {
				return 1 + FirstMethod(n - 1, count + 1);
			}
		}
		return count;
	}
	
	public int FirstMethodSol(int n, int count) {
		if (n > 0) {
			if (n == 1) {
				return 1;
			} else if (n % 2 == 0) {
				if (n > 10)
					return 1 + FirstMethodSol(n / 10, count + 1);
				return 1 + FirstMethodSol(n / 2, count + 1);
			} else if (n % 2 != 0) {
				if (n > 10)
					return 1 + FirstMethodSol((n / 10) - 1, count + 1);
				return 1 + FirstMethodSol(n - 1, count + 1);
			}
		}
		return count;
	}
	
	public int SecMeth(int n, int count) {
		if (n > 0) {
			if (n == 1) {
				return 1;
			} else if (n % 2 == 0) {
				return 1 + SecMeth(n / 2, count + 1);
			} else if (n % 2 != 0) {
				return 1 + SecMeth(3 * n + 1, count + 1);
			}
		}
		return count;
	}
	
	public int SecMethOpt(int n, int count) {
		if (n > 0) {
			if (n == 1) {
				return 1;
			} else if (n % 2 == 0) {
				return 1 + SecMethOpt(n / 2, count + 1);
			} else if (n % 2 != 0) {
				return 1 + SecMethOpt(3 * n + 1, count + 1);
			}
		}
		return count;
	}
	
	public static void main(String[] args) {
		final ProblemOne demo = new ProblemOne("Problem set One");
		
		final ProblemOne demo2 = new ProblemOne("Problem set One", 2);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
		demo2.pack();
		RefineryUtilities.centerFrameOnScreen(demo2);
		demo2.setVisible(true);
	}
	
}
