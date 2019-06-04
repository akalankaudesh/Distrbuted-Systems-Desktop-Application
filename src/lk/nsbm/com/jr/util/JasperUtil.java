package lk.nsbm.com.jr.util;

import lk.nsbm.com.jr.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.Map;

public class JasperUtil {

    public static final int REPORT_CUSTOMER = 0;
    public static final int REPORT_ITEM = 1;
    public static final int REPORT_ORDER = 2;
    public static final int REPORT_BILL = 3;
    public static final int REPORT_EMPLOYEE = 4;

    public static void showReport(int report, Map<String, Object> params, Object datasource) throws JRException, SQLException {
        JasperReport compiledReport;
        JasperPrint filledReport;
        switch (report){
            case 0:
                compiledReport = (JasperReport) JRLoader.loadObject(JasperUtil.class.getResource("/lk/nsbm/com/jr/report/customer-report.jasper"));
                break;
            case 1:
                compiledReport = (JasperReport) JRLoader.loadObject(JasperUtil.class.getResource("/lk/nsbm/com/jr/report/item-report.jasper"));
                break;
            case 2:
                compiledReport = (JasperReport) JRLoader.loadObject(JasperUtil.class.getResource("/lk/nsbm/com/jr/report/order-report.jasper"));
                break;
            case 3:
                compiledReport = (JasperReport) JRLoader.loadObject(JasperUtil.class.getResource("/lk/nsbm/com/jr/report/bill.jasper"));
                break;
                case 4:
                compiledReport = (JasperReport) JRLoader.loadObject(JasperUtil.class.getResource("/lk/nsbm/com/jr/report/employee-report.jasper"));
                break;
            default:
                throw new RuntimeException("Invalid Report");
        }
        if (datasource instanceof  JRDataSource) {
            filledReport = JasperFillManager.fillReport(compiledReport, params, (JRDataSource) datasource);
        }else{
            filledReport = JasperFillManager.fillReport(compiledReport, params, DBConnection.getConnection());
        }
        JasperViewer.viewReport(filledReport,false);
    }

}
