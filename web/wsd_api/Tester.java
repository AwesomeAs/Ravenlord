package wsd_api;

import java.io.Console;
import java.util.Scanner;

public class Tester {
	
	public static void main(String[] args) {
		
		WSDAPI api = WSDAPI.getInstance();
		
		Console console = System.console();
		
		APIResponse info_resp = api.getProfile(1);
		
		System.out.println(info_resp.toString());
		
		System.out.println("Online: " + api.getUser().toString());
		
		System.out.println("GenPass1: " + api.generatePassword());
		System.out.println("GenPass2: " + api.generatePassword());
		System.out.println("GenPass3: " + api.generatePassword());
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Username: ");
		String username = sc.nextLine();
		String password = new String(console.readPassword("Password: "));
		sc.close();
		
		APIResponse login_resp = api.login(username, password);
		
		System.out.println("Success: " + login_resp.success() + ", error: " + login_resp.getError() + ", [" + login_resp.getID() + "] " +
				login_resp.getUsername() + ", rank: " + login_resp.getInt("rank"));
		
		APIResponse resp = api.getUser();
		
		System.out.println(resp.success() + ": " + resp.getError() + ", [" + resp.getID() + "] " + resp.getUsername());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
