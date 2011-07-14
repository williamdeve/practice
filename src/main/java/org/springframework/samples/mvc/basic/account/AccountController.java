package org.springframework.samples.mvc.basic.account;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import net.spy.memcached.MemcachedClient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		MemcachedClient client = null;
		try {
			client = new MemcachedClient(new InetSocketAddress("10.201.10.122",11211));
			System.out.println(client.get("2"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account a = new Account();
		a.setName(client.get("4").toString());
		model.addAttribute(a);
		client.shutdown();
		
		
		return "account/createForm";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String create(@Valid Account account, BindingResult result) {
		if (result.hasErrors()) {
			return "account/createForm";
		}
		String a = account.assignId().toString();
		this.accounts.put(account.assignId(), account);
		try {
			
			MemcachedClient client = new MemcachedClient(new InetSocketAddress("10.201.10.122",11211));
			client.add(a, 0, "11000");
			client.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/account/" + account.getId();
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String getView(@PathVariable Long id, Model model) {
		//Account account = this.accounts.get(id);
		Account account = new Account();
		MemcachedClient client = null;
try {
			
			client = new MemcachedClient(new InetSocketAddress("10.201.10.122",11211));
			
			System.out.print(client.get(String.valueOf(id)));
			//account.setName(client.get(String.valueOf(id)).toString());
			account.setName(client.get("4").toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		client.shutdown();
		model.addAttribute(account);
		return "account/view";
	}

}
