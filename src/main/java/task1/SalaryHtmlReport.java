package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReport {

    private final Connection connection;
    private final static String statement = "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join\" +\n" +
            "                    \"salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and\" +\n" +
            "                    \" sp.date >= ? and sp.date <= ? group by emp.id, emp.name";

    private final static String HTML_HEADER = "<html>" +
            "<body>" +
            "<table>" +
            "<tr>" +
            "<td>Employee</td>" +
            "<td>Salary</td>" +
            "</tr>";

    private final static String HTML_BODY =
            "<tr>" +
                    "<td>%s</td><td>%f</td>" +
                    "</tr>";

    private final static String HTML_TOTAL =
            "<tr>" +
                    "<td>Total</td><td>%f</td>" +
                    "</tr>";

    private final static String HTML_FOOTER =
            "</table>" +
                    "</body>" +
                    "</html>\n";

    public SalaryHtmlReport(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    private ResultSet getSalary(String departmentId, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(statement);
        try {
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
            ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps.executeQuery();
    }


    public String generateHtml(ResultSet salarySet) {
        double total = 0;
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append(HTML_HEADER);
        try {
            while (salarySet.next()) {
                resultingHtml.append(String.format(HTML_BODY, salarySet.getString("emp_name"), salarySet.getDouble("salary")));
                total += salarySet.getDouble("salary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultingHtml.append(String.format(HTML_TOTAL, total));
        resultingHtml.append(HTML_FOOTER);

        return resultingHtml.toString();
    }

    public String generateReport(final String departmentId, final LocalDate dateFrom, final LocalDate dateTo) throws SQLException {
        ResultSet salarySet = getSalary(departmentId, dateFrom, dateTo);
        if (salarySet != null)
            return generateHtml(salarySet);

        return null;
    }
}