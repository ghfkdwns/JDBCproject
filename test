public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/database_test?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "kjun0513");
			Statement st = con.createStatement();
			//String sqld = "INSERT INTO database_test.member VALUES(4, 'KIM', 'NOT','1998.01.22','ghfkdwns')";
		    //st.executeUpdate(sqld);
			
			String sql = "SELECT * FROM database_test.member";
			ResultSet rs = st.executeQuery(sql);
		
			
			if(rs.next()) {
				do{
					//@SuppressWarnings("unused")
					String title = rs.getString("username");
					String a = rs.getString("id");
					String b = rs.getString("dept");
					String c = rs.getString("birth");
					String d = rs.getString("email");
					System.out.printf("title: %s, id: %s, dept: %s, birth: %s, email: %s\n", title, a, b, c, d);
				} while(rs.next());
			} else {
				System.out.printf("null 입니다");
			}
			
			rs.close();
			st.close();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
