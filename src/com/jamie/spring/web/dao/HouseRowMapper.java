//package com.jamie.spring.web.dao;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.jdbc.core.RowMapper;
//
//public class HouseRowMapper implements RowMapper<House> {
//
//	@Override
//	public House mapRow(ResultSet rs, int rowNum) throws SQLException {
//		User user = new User();
//		user.setAuthority(rs.getString("authority"));
//		user.setEmail(rs.getString("email"));
//		user.setEnabled(true);
//		user.setName(rs.getString("name"));
//		user.setUsername(rs.getString("username"));
//		
//		House house = new House();
//
//		house.setId(rs.getInt("id"));
//		house.setAddress(rs.getString("address"));
//		house.setRent(rs.getInt("rent"));
//		house.setRooms(rs.getInt("rooms"));
//		house.setUser(user);
//
//		return house;
//	}
//}
