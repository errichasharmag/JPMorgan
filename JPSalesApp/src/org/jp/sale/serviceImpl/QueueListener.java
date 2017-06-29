package org.jp.sale.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.jp.sale.beans.AbstractQueueListener;
import org.jp.sale.beans.Record;
import org.json.JSONArray;
import org.json.JSONObject;

public class QueueListener extends AbstractQueueListener<String> {

	private int count; 
	HashMap<String,Record> recordMap = null;
	JSONObject obj = null;
	
	public QueueListener() {
		this.count = 0;
		this.recordMap = new HashMap<String,Record>();
	}//End Of Contructor
	
	public int getCount() {
		return this.count;
	}
	
	@Override
	protected void onMessage(String message) {
		
		if( message!=null )		
		{
			try 
			{
				/* Parse received json message into Salerecords object*/
				obj = new JSONObject(message);
				JSONArray array = obj.getJSONArray("items");
				for(int j = 0; j< array.length(); j++)
				{
					JSONObject jsonObj = array.getJSONObject(j);
					/*Record sales in hashMap and update count and price on every message*/
					if(recordMap.containsKey(jsonObj.get("name")))
					{
						Record rec = recordMap.get(jsonObj.get("name"));
						int saleCount = rec.getCount() + Integer.parseInt(""+jsonObj.get("count"));
						int price = rec.getPrice() + Integer.parseInt(""+jsonObj.get("price"));
					
						rec.setCount(saleCount);
						rec.setPrice(price);
						recordMap.put(""+jsonObj.get("name"), rec);
					}
					else
					{
						Record record = new Record(""+jsonObj.get("name"),Integer.parseInt(""+jsonObj.get("count")),Integer.parseInt(""+jsonObj.get("price")));
						recordMap.put(""+jsonObj.get("name"), record);
					}
				}
				this.count++;
				if( this.count%10 == 0 )	
				{
					//TODO print report
					System.out.println(" *******Logging detailed report********* ");
					Set<String> items = recordMap.keySet();
					Iterator<String> iter = items.iterator();
					while(iter.hasNext())
					{
						
						Record itemRecord = recordMap.get(iter.next());
						
						System.out.println("Item name = "+itemRecord.getName()+" , total sale = "+itemRecord.getCount()+" , total price = "+itemRecord.getPrice() );
					}
				}
				
				
				if( this.count%50 == 0 )	{
					System.out.println("Consumer Paused, stop accepting new messages");
					super.pause();
					//TODO print report
					System.out.println(" Adjustments to be made ");
					System.out.println("Resuming consumer, start accepting new messages");
					super.resume();
				}
				
			} catch (Exception e) {
				System.err.println(" Error Parsing Message JSON ");
				e.printStackTrace();
			}//End Of Try Catch
		}else
			System.err.println(" Unknown Message to Parse ");
		
	}//End Of Listener


}
