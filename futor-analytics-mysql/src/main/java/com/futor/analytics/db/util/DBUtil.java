package com.futor.analytics.db.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil {
	
	 /**
     * This method frees ResultSet, SQL statement and database connection.
     * @param ResultSet rs, Statement stmt, Connection con
     * @return none
     * @throws none
     */
public static void freeResources(ResultSet rs, Statement stmt, Connection con){
	try {
        if (rs != null) {
            rs.close();
        }
	}
	catch (Exception e) {
	}

	try {
        if (stmt != null) {
            stmt.close() ;
        }
	}
	catch (Exception e) {
	}

	try {
        if (con != null) {
            con.close()  ;
        }
	}
	catch (Exception e) {
	}
}

}
