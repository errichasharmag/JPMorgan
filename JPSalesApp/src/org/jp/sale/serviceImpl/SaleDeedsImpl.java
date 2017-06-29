package org.jp.sale.serviceImpl;

import java.util.Queue;

import org.jp.sale.service.SaleDeeds;
import org.springframework.stereotype.Service;

@Service("saleDeeds")
public class SaleDeedsImpl implements SaleDeeds	{

	private Queue<String> queue;
	
	public SaleDeedsImpl() {
		QueueListener listener = new QueueListener();
		this.queue = listener.getQueue();
		listener.start();
	}//End of Constructor

	@Override
	public boolean process(String message) {
		return this.queue.add(message);
	}

}
