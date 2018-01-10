package com.VidyabhiSol.smartcredituse;

import java.util.ArrayList;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.VidyabhiSol.smartcredituse.Data.CardAnalyticsDataBin;
import com.VidyabhiSol.smartcredituse.Helpers.DBHelper;
import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility.AnalyticsGroupByClause;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class CardAnalytics extends Activity {
	DBHelper helper;
	List<CardAnalyticsDataBin> lstAnalytics;
	private GraphicalView mChart;
	RelativeLayout viewVenderWiseChart,viewCardWiseChart,viewDateWiseSpending;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_analytics);
		helper = new DBHelper(this);
		
		viewVenderWiseChart = (RelativeLayout)findViewById(R.id.CardAnalytics_chartViewVendor);
		viewCardWiseChart = (RelativeLayout)findViewById(R.id.CardAnalytics_chartViewCards);
		viewDateWiseSpending = (RelativeLayout)findViewById(R.id.CardAnalytics_chartViewDateWise);
		refreshChart(AnalyticsGroupByClause.vendor, viewVenderWiseChart);
		refreshChart(AnalyticsGroupByClause.card, viewCardWiseChart);
		if(mChart!=null)
			viewDateWiseSpending.removeView(mChart);
		
		mChart = getChardViewDateWise();
		if(mChart!=null)
			viewDateWiseSpending.addView(mChart);
	}
	
	private void refreshChart(AnalyticsGroupByClause clause, RelativeLayout layout){
		lstAnalytics = helper.getDataForVendorWiseTransactions(clause);
		CategorySeries series = new CategorySeries("pie");
		int cnt=0;
		String title="";
		for(CardAnalyticsDataBin bin:lstAnalytics){
			if(cnt>4)
				break;
			switch (clause){
				case card:
					title = "Vendorwise Distribution";
					series.add(bin.get_bankName() + "-" + bin.get_cardNumber(), bin.get_tranAmount());
					break;
				case tranDate:
					break;
				case vendor:
					title = "Cardwise Distribution";
					series.add(bin.get_tranVender(), bin.get_tranAmount());
					break;
			}
			
			cnt++;
		}
		if(mChart!=null)
			layout.removeView(mChart);
		
		mChart = getChartViewVendorWise(series,title);
		if(mChart!=null)
			layout.addView(mChart);
	}
	private GraphicalView getChartViewVendorWise(CategorySeries ser,String title)
	{
		GraphicalView gv = null;
		int[] colToUse = new int[] {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}; 
		
//		int[] colToUse = new int[] {Color.rgb(183, 183, 183),
//				Color.rgb(124, 175, 221), Color.rgb(255, 192, 0)
//				,Color.rgb(248, 206, 177), Color.rgb(140, 193, 104)};
		DefaultRenderer renderer = null;
		renderer = new DefaultRenderer();
		for(int cnt=0;cnt<ser.getItemCount();cnt++){
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colToUse[cnt]);
            r.setChartValuesTextSize(20);
            r.setDisplayChartValues(true);
            renderer.addSeriesRenderer(r);
        }
			renderer.isInScroll();
			renderer.setPanEnabled(false);
			renderer.setZoomEnabled(false);
	        renderer.setLegendTextSize(20);
	        renderer.setMargins(new int[] {0,0,0,0});
	        renderer.setLabelsColor(Color.BLACK);
	        renderer.setAxesColor(Color.BLACK);
	        renderer.setDisplayValues(true);
	        gv = ChartFactory.getPieChartView(this, ser, renderer);	
		return gv;
		
	}
	private GraphicalView getChardViewDateWise(){
		lstAnalytics = helper.getDataForVendorWiseTransactions(AnalyticsGroupByClause.tranDate);
		XYSeries tranSeries = new XYSeries("Transactions");
		List<String> xLabelDates = new ArrayList<String>();
		double tranAmt=0;
		String key;
		int cnt=0;
		double yMax = 0;
		for(CardAnalyticsDataBin bin:lstAnalytics){
			key = String.valueOf(bin.getTranMonth()) + "-" +  String.valueOf(bin.getTranYear());
			tranAmt = bin.get_tranAmount();
			tranSeries.add(cnt, tranAmt);
			xLabelDates.add(key);
			if(tranAmt>yMax){
				yMax = tranAmt;
			}
			cnt++;
		}
		 //Collections.sort(xLabelDates);
		 XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	     // Adding Income Series to the dataset
	     dataset.addSeries(tranSeries);
	     XYSeriesRenderer tranRenderer = new XYSeriesRenderer();
        tranRenderer.setColor(Color.rgb(130, 130, 230));
        tranRenderer.setFillPoints(true);
        tranRenderer.setLineWidth(2);
        tranRenderer.setDisplayChartValues(false);
        tranRenderer.setChartValuesTextSize(17);
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitleTextSize(20);
        multiRenderer.setMargins(new int[]{30, 65, 50, 30});
        multiRenderer.setAxisTitleTextSize(20);
        multiRenderer.setZoomEnabled(false, false);
        multiRenderer.setPanEnabled(false);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(yMax);
        multiRenderer.setXAxisMin(-1);
        multiRenderer.setXAxisMax(xLabelDates.size());
        multiRenderer.setMarginsColor(Color.WHITE);
        multiRenderer.setAxesColor(Color.BLACK);
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setYLabelsAlign(Align.RIGHT);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setLabelsColor(Color.BLACK);
        multiRenderer.setXLabelsAngle(45);
        multiRenderer.setXLabelsAlign(Align.LEFT);
        multiRenderer.setLabelsTextSize(12);
        multiRenderer.setBarWidth(20);
        multiRenderer.setBarSpacing(10);
        for(int i=0; i< xLabelDates.size();i++){
            multiRenderer.addXTextLabel(i, xLabelDates.get(i));
        }
        multiRenderer.addSeriesRenderer(tranRenderer);    
        GraphicalView gv = ChartFactory.getLineChartView(this, dataset, multiRenderer);    
		return gv;
		
	}

	
	
}
