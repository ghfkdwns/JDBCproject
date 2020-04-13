package com.hanshin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class homework1 {
	
	public static void CreateTable(){																		//테이블을 생성하는 함수
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			Statement st = con.createStatement();
			
			String cre = "CREATE TABLE if not exists addressbook (id INT not null , "
					+ "name VARCHAR(45), tel  VARCHAR(45), email  VARCHAR(60), address  VARCHAR(60))";
			// addressbook 테이블이 존재하지 않으면 생성
			st.execute(cre);
			
			String show = "SHOW TABLES like 'addressbook'"; // 테이블이 생성 되었는지 확인
			ResultSet rs1 = st.executeQuery(show);
			if(rs1.next())
				System.out.printf("테이블이 존재 합니다.\n\n");
			else
				System.out.printf("테이블이 존재 하지 않습니다.\n\n");
			
			rs1.close();
			st.close();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	public static void InsertData(){                                         										//테이블에 데이터 입력하는 함수
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan = new Scanner(System.in);

		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			PreparedStatement st = con.prepareStatement("INSERT INTO database_test.addressbook VALUES(?, ?, ?, ?, ?) ");
			
			while(true){                                                                               // 배열에 테이블의 id값들을 저장한다.
				
				String se = "SELECT id FROM database_test.addressbook";  // addressbook의 테이블의 id 값만을 받기위한 문장
				ResultSet rs3 = st.executeQuery(se);
				
				int h [];
				int ab = 0;
				h = new int [100];
				while(rs3.next()){
					h[ab] = rs3.getInt("id");
					ab++;
				}
				rs3.close();
				System.out.printf("데이터를 입력하시겠습니까? Yes면 1 NO면 0 \n");
				int a = scan.nextInt();
			
				if(a == 1){
					System.out.printf("입력할 번호: " + "\n");
					int ID = scan.nextInt();
					while(true){
							--ab;
							if(ID == h[ab]){
								break;
							}
							else if(ab ==0)
								break;
					}
					if(ID == h[ab]){	                                                           // 입력받은 ID값이 테이블에 이미 있으면 종료한다.
						System.out.printf("아이디 값이 중복됩니다.\n" + "처음으로 돌아갑니다 \n\n");
						ab = 0;                                             //ab 값을 초기화
						break;
					}
					
					System.out.printf("입력할 이름: "+"\n");
					String NAME = scan.next();			
	
					System.out.printf("입력할 전화번호: "+"\n");
					String TEL = scan.next();
	
					System.out.printf("입력할 이메일:  "+"\n");
					String EMAIL = scan.next();
				
					System.out.printf("입력할 주소:  "+"\n");
					String ADDRESS = scan.next();
					
					st.setInt(1, ID);
					st.setString(2, NAME);
					st.setString(3, TEL);
					st.setString(4, EMAIL);
					st.setString(5, ADDRESS);
					
					int cnt = st.executeUpdate();
					
					if(cnt != 0){
						System.out.println("데이터가 추가 되었습니다.\n");
						
						String sq2 = "SELECT * FROM database_test.addressbook";
						ResultSet rs4 = st.executeQuery(sq2);
						
						if(rs4.next()) {
							System.out.printf("데이터를 출력합니다.\n");
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
							System.out.printf("데이터가 없습니다.\n");
						}
					
					}
					else
						System.out.printf("데이터가 추가 되지 않았습니다. \n");
				}
				else if(a == 0){
					System.out.printf("종료 하겠습니다.\n\n");
					break;
				}
				else
					System.out.printf("잘 못 입력 하셨습니다. \n");				
			}
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	public static void UpdateData() {                                                                                  // 테이블을 수정하는 함수
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan2 = new Scanner(System.in);

		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			PreparedStatement st = con.prepareStatement("UPDATE database_test.addressbook SET email = "
																							+ "replace(email, ?, ?) ");          //email행의 특정 값만 바꾸기 위한 문장
			
			
			while(true){
				String sqld = "SELECT * FROM database_test.addressbook";
				ResultSet rst = st.executeQuery(sqld);
				
				if(rst.next()) {
					System.out.printf("데이터를 출력합니다. \n");
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
					System.out.printf("데이터가 없습니다.\n");
				}
				
				rst.close();
				
				System.out.printf("데이터를 바꾸겠습니까? Yes면 1 NO면 0 \n");
				int a = scan2.nextInt();
			
				if(a == 1){
					
					System.out.printf("바꾸고 싶은 이메일의 도메인 입력: \n");
					String EMAIL = scan2.next();
					
					System.out.printf("바꿀 도메인을 입력하세요: \n");
					String CEMAIL = scan2.next();
					
					st.setString(1, EMAIL);
					st.setString(2, CEMAIL);
					
					int cnt2 = st.executeUpdate(); 
					if(cnt2 != 0)
						System.out.println("데이터가 업데이트 되었습니다");	
					else
						System.out.printf("데이터가 업데이트 되지 않았습니다.");
				}
				else if(a == 0){
					System.out.printf("종료하겠습니다.");
					break;
				}	
				else
					System.out.printf("잘 못 입력 하셨습니다.");
				
			}
			
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}

	public static void DeleteData(){																				//데이터를 삭제하는 함수
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

			System.out.printf("데이터를 삭제하겠습니까?(하위 2개가 삭제됩니다) Yes면 1 NO면 0 \n");
			int del = scan4.nextInt();
			
			if(del == 1){
				for(int y = 0; y <2; y++){                            // 하위 2개의 데이터만 지우는 반복문
					--a;
					String sqlt = "DELETE FROM database_test.addressbook WHERE id = ";
					int result = st.executeUpdate(sqlt + i[a]); // 위 문장에 i[a]에 저장되어있는 값 연결
					
					if(result == 0)
						System.out.printf("삭제되지 않았습니다.\n\n");
					else
						System.out.printf("ID번호"+i[a]+"의 데이터가 삭제되었습니다.\n\n");
				}
			}
			else
				System.out.printf("종료 하겠습니다.\n");
		
			String sq6 = "SELECT * FROM database_test.addressbook";
			ResultSet rs6 = st.executeQuery(sq6);
			
			if(rs6.next()) {
				System.out.printf("데이터를 출력합니다.\n");
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
				System.out.printf("데이터가 없습니다.\n");
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
			System.out.printf("데이터를 출력합니다.\n");
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
			System.out.printf("데이터가 없습니다.\n");
		}
		
		
		rs.close();
		st.close();
		con.close();			
	} catch (Exception e) {
		e.printStackTrace();
	} 	
}
	
	
	public static void main(String[] args) {																	//메인함수
		// TODO Auto-generated method stub
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		Scanner scan1 = new Scanner(System.in);														//값을 입력받기 위한 Scanner
		
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			
			Statement st = con.createStatement();
			
			while(true){
				System.out.printf("무엇을 하겠습니까? \n"+ 
										"1(테이블 생성), 2(데이터 입력), 3(데이터 업데이트), 4(데이터 삭제), 5(테이블 출력), 6(종료) \n ");
				int num = 0;
				num = scan1.nextInt();
				
				switch (num){
				case 1:
					CreateTable(); // 테이블을 생성하는 함수
					num =0;
					break;
				case 2:
					InsertData(); // 데이터를 입력하는 함수
					num =0;
					break;
				case 3:
					UpdateData(); //데이터를 업데이트 하는 함수
					break;
				case 4:
					DeleteData(); // 데이터를 삭제하는 함수(하위 2개만)
					break;
				case 5:
					PrintTable(); //데이터를 출력하는 함수
					break;
				case 6:
					System.out.printf("종료 합니다.\n");
					break;
				default:
					System.out.printf("숫자를 잘 못 입력하셨습니다.\n");
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
