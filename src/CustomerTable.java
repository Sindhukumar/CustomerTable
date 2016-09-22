import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerTable {

	public static Connection con = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;

	public static void main(String[] args) {
		search();
		close();
	}

	public static void search() {
		System.out.println("Enter the last name of the customer to search:");
		SearchDB(getInput());
	}

	public static ResultSet queryDB(String sql) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// con = DriverManager.getConnection("jdbc:oracle:thin:sys as
			// sysdba/oracle@localhost:1521:orcl");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return rs;

	}

	public static void close() {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void SearchDB(String lastname) {
		String sql = "select customernumber, Title, fullname, streetaddress,city,state,zipcode,emailaddress,position,company from customers where upper(lastname) = upper('"
				+ lastname + "')";
		rs = queryDB(sql);
		try {
			if (!rs.isBeforeFirst()) {
				System.out.println("No records found.. Do you want to enter new customer? (yes/no)");
				if (getInput().equalsIgnoreCase("yes")) {
					addRow();
				}

			} else {
				while (rs.next()) {
					System.out.print("Customer Id: " + (rs.getInt(1) + "\n"));
					System.out.print((rs.getString(2) + " " + (rs.getString(3) + "\n"))); // title
																							// fullname
					System.out.print((rs.getString(4) + "\n"));// address
					System.out.print((rs.getString(5) + ", " + rs.getString(6) + " " + rs.getString(7) + "\n"));// city,
																												// sate
																												// zip
					System.out.print((rs.getString(8) + "\n"));// email
					System.out.print((rs.getString(9) + " at " + rs.getString(10)));// position
																					// at
																					// company
					System.out.println("\n");
				}
				System.out.println(
						"Press (1) to search for another customer or press (2) to Edit the customer's address. Press any other key to exit");
				switch (getInput()) {
				case "1":
					search();
					break;
				case "2":
					edit(lastname);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void edit(String lastname) {
		String  streetaddress, city, state, zipcode, sql;
		System.out.print("Enter streetaddress: ");
		streetaddress = getInput();
		System.out.print("Enter city: ");
		city = getInput();
		System.out.print("Enter State: ");
		state = getInput();
		System.out.print("Enter Zipcode: ");
		zipcode = getInput();
		sql = "update customers set streetaddress = '"+streetaddress+"', city = '"+city+"', state = '"+state+"', zipcode = '"+zipcode+"' where lastname = '"+lastname+"'";
		queryDB(sql);
		try {
			if (rs.isBeforeFirst())
				System.out.println(" customer address edited sucessfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addRow() {
		String title, fullname, firstname, lastname, streetaddress, city, state, zipcode, emailaddress, position,
				company, sql;
		System.out.print("Enter title:");
		title = getInput();
		System.out.print("Enter Full name: ");
		fullname = getInput();
		System.out.print("Enter First name: ");
		firstname = getInput();
		System.out.print("Enter Last name: ");
		lastname = getInput();
		System.out.print("Enter streetaddress: ");
		streetaddress = getInput();
		System.out.print("Enter city: ");
		city = getInput();
		System.out.print("Enter State: ");
		state = getInput();
		System.out.print("Enter Zipcode: ");
		zipcode = getInput();
		System.out.print("Enter email address: ");
		emailaddress = getInput();
		System.out.print("Enter Position: ");
		position = getInput();
		System.out.print("Enter Company/Employer: ");
		company = getInput();
		sql = "insert into customers(title, fullname, firstname, lastname, streetaddress, city, state, zipcode, emailaddress, position,	company) values ( "
				+ "'" + title + "','" + fullname + "','" + firstname + "','" + lastname + "','" + streetaddress + "','"
				+ city + "','" + state + "','" + zipcode + "','" + emailaddress + "','" + position + "','" + company
				+ "')";
		queryDB(sql);
		try {
			if (rs.isBeforeFirst())
				System.out.println("New customer added sucessfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getInput() {
		String input = "";
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		// sc.close();
		return input;

	}

}
