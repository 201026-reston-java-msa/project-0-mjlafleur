package com.revature.util;

import com.revature.model.User;

import java.util.List;

import com.revature.model.Account;

public class AccountInfo {
	
	public static void accountInfo(User targetUser, List<Account> targetAccounts) {
		
		if(targetUser.getRolenum()>=3) {
	System.out.println("Welcome "+targetUser.getFirstName()+" "+targetUser.getLastName()+"\n"
			+"Current accounts:");
			
		} else if (targetUser.getRolenum()==2) {
			System.out.println("Welcome "+ targetUser.getFirstName()+" "+targetUser.getLastName()+"\n");
			
		} else if (targetUser.getRolenum()==1) {
			System.out.println("Welcome "+ targetUser.getFirstName()+" "+targetUser.getLastName()+"\n");
		}
			
	
	
	
	
	}

}
