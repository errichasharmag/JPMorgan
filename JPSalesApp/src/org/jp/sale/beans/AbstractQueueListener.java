package org.jp.sale.beans;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractQueueListener<T> implements Runnable {

	private BlockingQueue<T> queue;
	private boolean running, paused;
	private Thread thread;
	private Object lock;
	
	public AbstractQueueListener() {
		this.queue = new LinkedBlockingQueue<T>();
		this.running = this.paused = false;
		this.lock = new Object();
	}//End Of Constructor
	
	public Queue<T> getQueue() {
		return this.queue;
	}
	
	public void start() {
		this.thread = new Thread(this);
		this.running = true;
		this.thread.start();
	}
	
	public void pause() {
		this.paused = true;
	}
	
	public void resume() {
		synchronized (this.lock) {
			this.paused = false;
			this.lock.notify();
		}
	}
	
	public void stop()	{
		this.running = false;
		this.thread.interrupt();
	}
	
	@Override
	public void run()  {
		while(this.running)	{
			
			T msg = null;
			
			try {
				msg = this.queue.take();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if( msg != null )	{
				this.onMessage(msg);
			}
			
			if( this.paused )	{
					try {
						synchronized (this.lock) {
							this.lock.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
		}//End Of Loop
	}//End Of Thread
	
	protected abstract void onMessage(T message);
	
}
