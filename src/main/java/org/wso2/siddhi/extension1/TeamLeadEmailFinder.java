package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.sql.*;

/**
 * Created by danula on 3/13/15.
 */
public class TeamLeadEmailFinder implements OutputAttributeAggregator {

    private static final java.lang.String DB_URL = "jdbc:mysql://localhost:3306/emails";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123";
    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.STRING;
    }

    @Override
    public Object processAdd(Object o) {
        String product = (String)o;
        return getEmailRecipients(product);
    }

    @Override
    public Object processRemove(Object o) {
        String product = (String)o;
        return getEmailRecipients(product);
    }

    @Override
    public OutputAttributeAggregator newInstance() {
        return new TeamLeadEmailFinder();
    }

    @Override
    public void destroy() {

    }

    private String getEmailRecipients(String product) {
        Connection connection = null;
        Statement statement = null;
        String email = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            statement = connection.createStatement();
            String sql;
            sql = "SELECT teamLead FROM components WHERE product = '"+product+"' LIMIT 1";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            email = rs.getString("teamLead");
            rs.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
}
