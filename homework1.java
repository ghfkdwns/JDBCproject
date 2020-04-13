package com.hanshin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class homework1 {
	
	public static void CreateTable(){																		//���̺��� �����ϴ� �Լ�
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			Statement st = con.createStatement();
			
			String cre = "CREATE TABLE if not exists addressbook (id INT not null , "
					+ "name VARCHAR(45), tel  VARCHAR(45), email  VARCHAR(60), address  VARCHAR(60))";
			// address ���̺��� �������� ������ ����
			st.execute(cre);
			
			String show = "SHOW TABLES like 'addressbook'"; // ���̺��� ���� �Ǿ����� Ȯ��
			ResultSet rs1 = st.executeQuery(show);
			if(rs1.next())
				System.out.printf("���̺��� ���� �մϴ�.\n\n");
			else
				System.out.printf("���̺��� ���� ���� �ʽ��ϴ�.\n\n");
			
			rs1.close();
			st.close();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	public static void InsertData(){                                         										//���̺� ������ �Է��ϴ� �Լ�
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan = new Scanner(System.in);

		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			PreparedStatement st = con.prepareStatement("INSERT INTO database_test.addressbook VALUES(?, ?, ?, ?, ?) ");
			
			while(true){                                                                               // �迭�� ���̺��� id������ �����Ѵ�.
				
				String se = "SELECT id FROM database_test.addressbook";  // addressbook�� ���̺��� id ������ �ޱ����� ����
				ResultSet rs3 = st.executeQuery(se);
				
				int h [];
				int ab = 0;
				h = new int [100];
				while(rs3.next()){
					h[ab] = rs3.getInt("id");
					ab++;
				}
				rs3.close();
				System.out.printf("�����͸� �Է��Ͻðڽ��ϱ�? Yes�� 1 NO�� 0 \n");
				int a = scan.nextInt();
			
				if(a == 1){
					System.out.printf("�Է��� ��ȣ: " + "\n");
					int ID = scan.nextInt();
					while(true){
							--ab;
							if(ID == h[ab]){
								break;
							}
							else if(ab ==0)
								break;
					}
					if(ID == h[ab]){	                                                           // �Է¹��� ID���� ���̺� �̹� ������ �����Ѵ�.
						System.out.printf("���̵� ���� �ߺ��˴ϴ�.\n" + "ó������ ���ư��ϴ� \n\n");
						ab = 0;                                             //ab ���� �ʱ�ȭ
						break;
					}
					
					System.out.printf("�Է��� �̸�: "+"\n");
					String NAME = scan.next();			
	
					System.out.printf("�Է��� ��ȭ��ȣ: "+"\n");
					String TEL = scan.next();
	
					System.out.printf("�Է��� �̸���:  "+"\n");
					String EMAIL = scan.next();
				
					System.out.printf("�Է��� �ּ�:  "+"\n");
					String ADDRESS = scan.next();
					
					st.setInt(1, ID);
					st.setString(2, NAME);
					st.setString(3, TEL);
					st.setString(4, EMAIL);
					st.setString(5, ADDRESS);
					
					int cnt = st.executeUpdate();
					
					if(cnt != 0){
						System.out.println("�����Ͱ� �߰� �Ǿ����ϴ�.\n");
						
						String sq2 = "SELECT * FROM database_test.addressbook";
						ResultSet rs4 = st.executeQuery(sq2);
						
						if(rs4.next()) {
							System.out.printf("�����͸� ����մϴ�.\n");
							do{
								//@SuppressWarnings("unused")
								String name = rs4.getString("name");
								int id = rs4.getInt("id");
								String tel = rs4.getString("tel");
								String address = rs4.getString("address");
								String email = rs4.getString("email");
								System.out.printf(" id: %d, Name: %s, tel: %s, email: %s, address: %s\n\n ", id, name, tel, email, address);
							} while(rs4.next());
							
							rs4.close();
						} else {
							System.out.printf("�����Ͱ� �����ϴ�.\n");
						}
					
					}
					else
						System.out.printf("�����Ͱ� �߰� ���� �ʾҽ��ϴ�. \n");
				}
				else if(a == 0){
					System.out.printf("���� �ϰڽ��ϴ�.\n\n");
					break;
				}
				else
					System.out.printf("�� �� �Է� �ϼ̽��ϴ�. \n");				
			}
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	public static void UpdateData() {                                                                                  // ���̺��� �����ϴ� �Լ�
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan2 = new Scanner(System.in);

		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			PreparedStatement st = con.prepareStatement("UPDATE database_test.addressbook SET email = "
																							+ "replace(email, ?, ?) ");          //email���� Ư�� ���� �ٲٱ� ���� ����
			
			
			while(true){
				String sqld = "SELECT * FROM database_test.addressbook";
				ResultSet rst = st.executeQuery(sqld);
				
				if(rst.next()) {
					System.out.printf("�����͸� ����մϴ�. \n");
					do{
						//@SuppressWarnings("unused")
						String name = rst.getString("name");
						int id = rst.getInt("id");
						String tel = rst.getString("tel");
						String address = rst.getString("address");
						String email = rst.getString("email");
						System.out.printf(" id: %d, Name: %s, tel: %s, email: %s, address: %s\n ", id, name, tel, email, address);
					} while(rst.next());
				} else {
					System.out.printf("�����Ͱ� �����ϴ�.\n");
				}
				
				rst.close();
				
				System.out.printf("�����͸� �ٲٰڽ��ϱ�? Yes�� 1 NO�� 0 \n");
				int a = scan2.nextInt();
			
				if(a == 1){
					
					System.out.printf("�ٲٰ� ���� �̸����� ������ �Է�: \n");
					String EMAIL = scan2.next();
					
					System.out.printf("�ٲ� �������� �Է��ϼ���: \n");
					String CEMAIL = scan2.next();
					
					st.setString(1, EMAIL);
					st.setString(2, CEMAIL);
					
					int cnt2 = st.executeUpdate(); 
					if(cnt2 != 0)
						System.out.println("�����Ͱ� ������Ʈ �Ǿ����ϴ�");	
					else
						System.out.printf("�����Ͱ� ������Ʈ ���� �ʾҽ��ϴ�.");
				}
				else if(a == 0){
					System.out.printf("�����ϰڽ��ϴ�.");
					break;
				}	
				else
					System.out.printf("�� �� �Է� �ϼ̽��ϴ�.");
				
			}
			
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}

	public static void DeleteData(){																				//�����͸� �����ϴ� �Լ�
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan4 = new Scanner(System.in);
		
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			Statement st = con.createStatement();
			
			String sql = "SELECT id FROM database_test.addressbook";
			ResultSet rs5 = st.executeQuery(sql);
			
			int i [];
			int a = 0;
			i = new int [100];
			while(rs5.next()){
				i[a] = rs5.getInt("id");
				a++;
			}

			System.out.printf("�����͸� �����ϰڽ��ϱ�?(���� 2���� �����˴ϴ�) Yes�� 1 NO�� 0 \n");
			int del = scan4.nextInt();
			
			if(del == 1){
				for(int y = 0; y <2; y++){                            // ���� 2���� �����͸� ����� �ݺ���
					--a;
					String sqlt = "DELETE FROM database_test.addressbook WHERE id = ";
					int result = st.executeUpdate(sqlt + i[a]); // �� ���忡 i[a]�� ����Ǿ��ִ� �� ����
					
					if(result == 0)
						System.out.printf("�������� �ʾҽ��ϴ�.\n\n");
					else
						System.out.printf("ID��ȣ"+i[a]+"�� �����Ͱ� �����Ǿ����ϴ�.\n\n");
				}
			}
			else
				System.out.printf("���� �ϰڽ��ϴ�.\n");
		
			String sq6 = "SELECT * FROM database_test.addressbook";
			ResultSet rs6 = st.executeQuery(sq6);
			
			if(rs6.next()) {
				System.out.printf("�����͸� ����մϴ�.\n");
				do{
					//@SuppressWarnings("unused")
					String name = rs6.getString("name");
					int id = rs6.getInt("id");
					String tel = rs6.getString("tel");
					String address = rs6.getString("address");
					String email = rs6.getString("email");
					System.out.printf(" id: %d, Name: %s, tel: %s, email: %s, address: %s\n\n ", id, name, tel, email, address);
				} while(rs6.next());
				
				rs6.close();
			} else {
				System.out.printf("�����Ͱ� �����ϴ�.\n");
			}
			
			rs5.close();
			st.close();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
public static void PrintTable(){
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
	try {
		Class.forName(jdbc_driver).newInstance();
		Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
		Statement st = con.createStatement();
		
		String sql = "SELECT * FROM database_test.addressbook";
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next()) {
			System.out.printf("�����͸� ����մϴ�.\n");
			do{
				//@SuppressWarnings("unused")
				String name = rs.getString("name");
				int id = rs.getInt("id");
				String tel = rs.getString("tel");
				String address = rs.getString("address");
				String email = rs.getString("email");
				System.out.printf(" id: %d, Name: %s, tel: %s, email: %s, address: %s\n ", id, name, tel, email, address);
			} while(rs.next());
		} else {
			System.out.printf("�����Ͱ� �����ϴ�.\n");
		}
		
		
		rs.close();
		st.close();
		con.close();			
	} catch (Exception e) {
		e.printStackTrace();
	} 	
}
	
	
	public static void main(String[] args) {																	//�����Լ�
		// TODO Auto-generated method stub
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan1 = new Scanner(System.in);														//���� �Է¹ޱ� ���� Scanner
		
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			
			Statement st = con.createStatement();
			
			while(true){
				System.out.printf("������ �ϰڽ��ϱ�? \n"+ 
										"1(���̺� ����), 2(������ �Է�), 3(������ ������Ʈ), 4(������ ����), 5(���̺� ���), 6(����) \n ");
				int num = 0;
				num = scan1.nextInt();
				
				switch (num){
				case 1:
					CreateTable(); // ���̺��� �����ϴ� �Լ�
					num =0;
					break;
				case 2:
					InsertData(); // �����͸� �Է��ϴ� �Լ�
					num =0;
					break;
				case 3:
					UpdateData(); //�����͸� ������Ʈ �ϴ� �Լ�
					break;
				case 4:
					DeleteData(); // �����͸� �����ϴ� �Լ�(���� 2����)
					break;
				case 5:
					PrintTable(); //�����͸� ����ϴ� �Լ�
					break;
				case 6:
					System.out.printf("���� �մϴ�.\n");
					break;
				default:
					System.out.printf("���ڸ� �� �� �Է��ϼ̽��ϴ�.\n");
				}
				
				if(num == 6)
					break;
			}
			
			
			st.close();
			con.close();
			scan1.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	  }
	}
