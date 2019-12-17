package com.karaoke.management.report;

import java.time.LocalDateTime;
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
		billReportMetaData.setRoomName(room.getRoomName());
		billReportMetaData.setSeller(seller);
		billReportMetaData.setBillDetailReports(builBillDetailReport(room, bill, billDetails));
		
		return billReportMetaData;
	}

	private static ArrayList<BillDetailReport> builBillDetailReport(Room room, Bill bill, List<BillDetails> billDetails) {
		ArrayList<BillDetailReport> billDetailReports = new ArrayList<BillDetailReport>();
		
		RoomType roomType = room.getRoomType();
		
		billDetailReports.add(buildBillReport(roomType.getTypeName(), buildUnitPrice(roomType.getPrice()), buildNumber(bill.getCheckin(), bill.getCheckout()), buildPrice(bill.getCheckin(), bill.getCheckout(), roomType.getPrice())));
		
		for (BillDetails billDetail : billDetails) {
			BillDetailReport billDetailReport = new BillDetailReport();
//			billDetailReport.setTypeName(typeName);
//			billDetailReport.setUnitPrice(unitPrice);
//			billDetailReport.setNumber(number);
//			billDetailReport.setPrice(price);
			billDetailReports.add(billDetailReport);
		}
		
		return billDetailReports;
	}

	private static String buildPrice(LocalDateTime checkin, LocalDateTime checkout, int price) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String buildNumber(LocalDateTime checkin, LocalDateTime checkout) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String buildUnitPrice(int price) {
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

}
