/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ods;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetPassword {

	Connection connect = null;
	String key = "USSD";

	public String getPass(String strSID) {
		String result = "";
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connect = new MyConnection().getConn();
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select Username, PASSWORD from ODS_credential where SID='" + strSID + "'");
			if (resultSet != null) {
				if (resultSet.next()) {
					result = "0|" + resultSet.getString(1) + "|" + Encryption.DecryptPass(resultSet.getString(2)) + "|";
				} else
					result = "-1|";
			} else {
				result = "-1|";
			}
		} catch (Exception e) {
			result = "-1|";
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	public String getPassAnotherDB(String strSID) {
		String result = "";
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connect = new MyConnection().getConn2();
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select Username, PASSWORD from ODS_credential where SID='" + strSID + "'");
			if (resultSet != null) {
				if (resultSet.next()) {
					result = "0|" + resultSet.getString(1) + "|" + Encryption.DecryptPass(resultSet.getString(2)) + "|";
				} else
					result = "-1|";
			} else {
				result = "-1|";
			}
		} catch (Exception e) {
			result = "-1|";
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public String getDetails() {
		String result = "";
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connect = new MyConnection().getConn();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select Username, PASSWORD, SID, EXPIRY_DATE from ODS_credential");
			if (resultSet != null) {
				while (resultSet.next()) {
					String dte = resultSet.getString(4);
					if (dte.length() > 10)
						dte = dte.split(" ")[0];
					result = result + resultSet.getString(1) + "|" + Encryption.DecryptPass(resultSet.getString(2))
							+ "|" + resultSet.getString(3) + "|" + resultSet.getString(4).split(" ")[0] + "~";
				}
				if (result.length() == 0)
					result = "1~";
			} else {
				result = "-1~";
			}
		} catch (Exception e) {
			result = "-1";
			System.err.println("getDetails-->"+e.getMessage());
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	public String getDetails2() {
		String result = "";
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connect = new MyConnection().getConn2();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select Username, PASSWORD, SID, EXPIRY_DATE from ODS_credential");
			if (resultSet != null) {
				while (resultSet.next()) {
					String dte = resultSet.getString(4);
					if (dte.length() > 10)
						dte = dte.split(" ")[0];
					result = result + resultSet.getString(1) + "|" + Encryption.DecryptPass(resultSet.getString(2))
							+ "|" + resultSet.getString(3) + "|" + resultSet.getString(4).split(" ")[0] + "~";
				}
				if (result.length() == 0)
					result = "1~";
			} else {
				result = "-1~";
			}
		} catch (Exception e) {
			result = "-1";
			System.err.println("getDetails2-->"+e.getMessage());
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}


	public int insertDB(String user, String pass, String sid, String strDate) {
		// boolean bool = false;
		int bool = -1;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			java.util.Date date = format.parse(strDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			connect = new MyConnection().getConn();
			stmt = connect.createStatement();
			res = stmt.executeQuery("SELECT * FROM ODS_credential WHERE sid='" + sid + "' ");
			if (res.next())
				return 0;
			pass = Encryption.EncryptPass(pass);
		
			preparedStatement = connect.prepareStatement("insert into  ODS_credential values (?, ?, ?, ?, ?)");
			preparedStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(2, user);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, sid);
			preparedStatement.setDate(5, sqlDate);
			boolean bl = true;
			bl = preparedStatement.execute();
			if (!bl)
				bool = 1;

		} catch (Exception e) {
			e.getMessage();
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				res.close();
				stmt.close();
				if (preparedStatement != null)
					preparedStatement.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bool;

	}
	public int insertDB2(String user, String pass, String sid, String strDate) {
		// boolean bool = false;
		int bool = -1;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			java.util.Date date = format.parse(strDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			connect = new MyConnection().getConn2();
			stmt = connect.createStatement();
			res = stmt.executeQuery("SELECT * FROM ODS_credential WHERE sid='" + sid + "' ");
			if (res.next())
				return 0;
			pass = Encryption.EncryptPass(pass);
		
			preparedStatement = connect.prepareStatement("insert into  ODS_credential values (?, ?, ?, ?, ?)");
			preparedStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(2, user);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, sid);
			preparedStatement.setDate(5, sqlDate);
			boolean bl = true;
			bl = preparedStatement.execute();
			if (!bl)
				bool = 1;

		} catch (Exception e) {
			e.getMessage();
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				res.close();
				stmt.close();
				if (preparedStatement != null)
					preparedStatement.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bool;

	}

	public int updateDB(String user, String pass, String sid, String strDate, String curStrSid) {
		int bool = -1;
		String result = "-1";
		Connection connect = null;
		Statement stmt = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			java.util.Date date = format.parse(strDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			pass = Encryption.EncryptPass(pass);
			String query = "update ODS_credential set INSERT_DATE_TIME=CURRENT_TIMESTAMP,Username='" + user
					+ "',PASSWORD='" + pass + "',sid='" + sid + "',EXPIRY_DATE= DATE '" + sqlDate + "' WHERE SID='"
					+ curStrSid + "' ";
			connect = new MyConnection().getConn();
			stmt = connect.createStatement();
			if (!sid.equals(curStrSid)) {

				ResultSet res = stmt.executeQuery("SELECT * FROM ODS_credential WHERE sid='" + sid + "' ");

				if (res.next()) {
					return 0;
				}
			}
			bool = stmt.executeUpdate(query);
			
		} catch (Exception e) {
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				stmt.close();
				connect.close();
			} catch (Exception e) {
				System.err.println("Rac1 is down :"+e.getMessage());
			}
		}
		return bool;
	}
	public int updateDB2(String user, String pass, String sid, String strDate, String curStrSid) {
		int bool = -1;
		String result = "-1";
		Connection connect = null;
		Statement stmt = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			java.util.Date date = format.parse(strDate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			pass = Encryption.EncryptPass(pass);
			String query = "update ODS_credential set INSERT_DATE_TIME=CURRENT_TIMESTAMP,Username='" + user
					+ "',PASSWORD='" + pass + "',sid='" + sid + "',EXPIRY_DATE= DATE '" + sqlDate + "' WHERE SID='"
					+ curStrSid + "' ";
			connect = new MyConnection().getConn2();
			stmt = connect.createStatement();
			if (!sid.equals(curStrSid)) {

				ResultSet res = stmt.executeQuery("SELECT * FROM ODS_credential WHERE sid='" + sid + "' ");

				if (res.next()) {
					return 0;
				}
			}
			bool = stmt.executeUpdate(query);
			
		} catch (Exception e) {
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				stmt.close();
				connect.close();
			} catch (Exception e) {
				System.err.println("Rac2 is down :"+e.getMessage());
			}
		}
		return bool;
	}
	public int deleteRAC1DB(String user, String pass, String sid) {
		int bool = -1;
		String result = "-1";
		Connection connect = null;
		PreparedStatement  stmt = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			
			pass = Encryption.EncryptPass(pass);
			String query = "delete from  ODS_credential where Username='" + user
					+ "'and PASSWORD='" + pass + "'and sid='" + sid+"'";
			System.out.println(query);
			connect = new MyConnection().getConn();
			stmt = connect.prepareStatement(query);
			
			bool = stmt.executeUpdate();
			
		} catch (Exception e) {
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				stmt.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bool;
	}
	public int deleteRAC2DB(String user, String pass, String sid) {
		int bool = -1;
		String result = "-1";
		Connection connect = null;
		PreparedStatement  stmt = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			
			pass = Encryption.EncryptPass(pass);
			String query = "delete from  ODS_credential where Username='" + user
					+ "'and PASSWORD='" + pass + "'and sid='" + sid+"'";
			connect = new MyConnection().getConn2();
			stmt = connect.prepareStatement(query);
			
			bool = stmt.executeUpdate();
			
		} catch (Exception e) {
			bool = -1;
			System.err.println(e.getMessage());
		} finally {
			try {
				stmt.close();
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bool;
	}
}
