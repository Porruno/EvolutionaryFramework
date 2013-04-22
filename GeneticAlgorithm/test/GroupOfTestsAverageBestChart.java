package test;

import geneticAlgorithm.chart.EvaluationsNumberFormatter;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;

import org.jfree.ui.RefineryUtilities;

public class GroupOfTestsAverageBestChart extends JFrame {

	private static final long serialVersionUID = 1L;

	public GroupOfTestsAverageBestChart(String applicationTittle, String chartTitle, TestResult[] testResults, int evaluationsPerGeneration){
		super(applicationTittle);

		XYDataset dataset = createDataset(testResults);
		int largestTestResult = 0;
		for(TestResult testResult : testResults){
			if(testResult.averageBest.length > largestTestResult){
				largestTestResult = testResult.averageBest.length;
			}
		}
		JFreeChart chart = createChart(dataset, largestTestResult, evaluationsPerGeneration);
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(XYDataset dataset, int generations, int evaluationsPerGeneration){
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Report", "Evaluations", "Aptitude", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		NumberAxis nA = (NumberAxis) plot.getDomainAxis();
		nA.setNumberFormatOverride(new EvaluationsNumberFormatter(evaluationsPerGeneration));

		return chart;
	}

	public XYDataset createDataset(TestResult[] testResults){
		XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
		for(TestResult testResult : testResults){
			XYIntervalSeries averageSerie = new XYIntervalSeries(testResult.testName);
			double[] averageBest = testResult.averageBest;
			for(int i = 0; i < averageBest.length; i++){
				averageSerie.add(i, i, i, averageBest[i], averageBest[i], averageBest[i]);
			}
			dataset.addSeries(averageSerie);
		}
		return dataset;
	}

	public static void graph(String name, TestResult[] testResults, int evaluationsPerGeneration){
		GroupOfTestsAverageBestChart chart = new GroupOfTestsAverageBestChart("Average Best", name, testResults, evaluationsPerGeneration);

		chart.pack();

		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);


	}
}