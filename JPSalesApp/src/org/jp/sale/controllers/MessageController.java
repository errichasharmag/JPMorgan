package org.jp.sale.controllers;

import javax.servlet.http.HttpServletRequest;

import org.jp.sale.service.SaleDeeds;
import org.jp.sale.serviceImpl.SaleDeedsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("/sales")
public class MessageController {

	@Autowired
	private SaleDeeds saleDeeds;
	
	public void setSaleDeeds(SaleDeeds saleDeeds) {
		this.saleDeeds = saleDeeds;
	}

	
	@ResponseBody
	@RequestMapping(value="/setMessage", method=RequestMethod.POST)
	public String processMessage( @RequestParam(value="message") String message, HttpServletRequest request )				{

		if( this.saleDeeds.process(message) )
			return "Success";
		else
			return "Failure";
		
	}//End Of Request

	public static void main(String args[])
	{
		
		String message= "{\"store\": \"M&S\",\"items\": [{\"name\" : \"tea\",\"count\": \"10\",\"price\": \"10\"},{\"name\" : \"coffee\",\"count\": \"5\",\"price\": \"20\"},{\"name\" : \"apple\",\"count\": \"3\",\"price\": \"20\"},{\"name\" : \"mango\",\"count\": \"9\",\"price\": \"70\"},{\"name\" : \"grapes\",\"count\": \"7\",\"price\": \"50\"},{\"name\" : \"orange\",\"count\": \"2\",\"price\": \"20\"}]}";
		SaleDeedsImpl saleDeeds = new SaleDeedsImpl();
		for(int i=0; i<51; i++)
			saleDeeds.process(message);
	}
}
