package com.karaoke.management.report;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.BillDetails;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.RoomType;

public class BuildBillReportMetadata {

	public static BillReportMetaData buildMetadata(Room room, Bill bill, List<BillDetails> billDetails, String seller) {
		BillReportMetaData billReportMetaData = new BillReportMetaData();
		
		billReportMetaData.setBillId(String.valueOf(bill.getBillId()));
		billReportMetaData.setStartDate(bill.getCheckin().toString());
		billReportMetaData.setEndDate(bill.getCheckout().toString());
		billReportMetaData.setTotalPrice(buildUnitPrice((double)bill.getTotal()));
		billReportMetaData.setRoomName(room.getRoomName());
		billReportMetaData.setSeller(seller);
		billReportMetaData.setBillDetailReports(builBillDetailReport(room, bill, billDetails));
		
		return billReportMetaData;
	}

	private static ArrayList<BillDetailReport> builBillDetailReport(Room room, Bill bill, List<BillDetails> billDetails) {
		ArrayList<BillDetailReport> billDetailReports = new ArrayList<BillDetailReport>();
		
		RoomType roomType = room.getRoomType();
		
		billDetailReports.add(buildBillReport(roomType.getTypeName(), buildUnitPrice((double)roomType.getPrice()), buildNumber(bill.getCheckin(), bill.getCheckout()), buildPrice(bill.getCheckin(), bill.getCheckout(), roomType.getPrice())));
		
		for (BillDetails billDetail : billDetails) {
			BillDetailReport billDetailReport = new BillDetailReport();
			billDetailReport.setTypeName(billDetail.getFood().getEatingName());
			billDetailReport.setUnitPrice(buildUnitPrice((double)billDetail.getFood().getPrice()));
			billDetailReport.setNumber(String.valueOf(billDetail.getNumber()));
			billDetailReport.setPrice(buildUnitPrice(billDetail.getUnitPrice()));
			billDetailReports.add(billDetailReport);
		}
		
		return billDetailReports;
	}

	private static String buildPrice(LocalDateTime checkin, LocalDateTime checkout, int price) {
		double result = calculateTimeMoney(checkin, checkout, (double)price);
		return result < 100000.0 ? buildUnitPrice(100000.0) : buildUnitPrice(result);
	}

	private static String buildNumber(LocalDateTime checkin, LocalDateTime checkout) {
		double result = calculateTime(checkin, checkout);
		return result < 0.5 ? String.valueOf(0.5) : String.valueOf(result);
	}

	private static String buildUnitPrice(double price) {
		return String.format("%,.2f", price);
	}

	private static BillDetailReport buildBillReport(String typeName, String unitPrice, String number, String price) {
		BillDetailReport billDetailReport = new BillDetailReport();
		
		billDetailReport.setTypeName(typeName);
		billDetailReport.setUnitPrice(unitPrice);
		billDetailReport.setNumber(number);
		billDetailReport.setPrice(price);
		
		return billDetailReport;
	}
	
	private static double calculateTimeMoney(LocalDateTime checkinTime, LocalDateTime checkoutTime, double priceRoom) {

		double result = 0;

		result = (double) calculateTime(checkinTime, checkoutTime) * priceRoom;

		return result;
	}
	
	private static double calculateTime(LocalDateTime checkinTime, LocalDateTime checkoutTime) {
	
		double result = 0;

		LocalDateTime tempDateTime = LocalDateTime.from(checkinTime);

		long hours = tempDateTime.until(checkoutTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);

		result += hours;

		long minutes = tempDateTime.until(checkoutTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);

		result += (double) minutes / 60;

		long seconds = tempDateTime.until(checkoutTime, ChronoUnit.SECONDS);

		result += (double) seconds / 3600;

		result = Math.round(result * 100.0) / 100.0;
		
		return result;
	
	}

}
