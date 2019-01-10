package com.brainmentors.los.utils;

import java.util.Scanner;

public class Utility implements StageConstants {
	public static int serialCounter=1;
	private Utility() {}
	public static Scanner scanner = new Scanner(System.in);
	public static String getStageName(int stageId) {
		switch (stageId) {
		case SOURCING:
			return "SOURCING Stage";
		case QDE:
			return "QUICK DATA ENTRY Stage";
		case DEDUPE:
			return "DEDUPE Stage";
		case SCORING:
			return "SCORING Stage";
		case APPROVAL:
			return "APPROVAL Stage";
		case REJECT:
			return "REJECTION Stage";
		default :
			return "Invalid Stage";
	}
	}

}
