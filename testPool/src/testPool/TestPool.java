package testPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;

import com.codestudio.sql.PoolMan;
import com.codestudio.sql.PoolManConnectionHandle;

public class TestPool {
	public static void main(String[] args) {
		TestPool tp = new TestPool();
		PoolManConnectionHandle poolcon = new TestPool().getConnection();
		tp.insertAlarm2DB(poolcon);
	}

	public PoolManConnectionHandle getConnection() {
		try {
			DataSource ds = PoolMan.findDataSource("MY_DB");
			PoolManConnectionHandle poolcon = (PoolManConnectionHandle) ds
					.getConnection();

			return poolcon;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void insert(PoolManConnectionHandle poolcon) {

		PreparedStatement ps = null;
		String sql = "insert into ALM_TTTASK (task_id, alarm_id, send_times, create_time, send_flag) values (?,?,?, sysdate, 0)";
		try {

			ps = poolcon.getPhysicalConnection().prepareStatement(sql);
			ps.setString(1, "t_1");
			ps.setString(2, "a_1");
			ps.setInt(3, 0);
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void insertRepeatAlarm(PoolManConnectionHandle poolcon) {
		PreparedStatement ps = null;
		String sql = "insert into alm_repeat  values (seq_alm_repeat.nextval,?,sysdate)";
		try {

			ps = poolcon.getPhysicalConnection().prepareStatement(sql);
			ps.setString(1, null);
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void getRepeatAlarm(PoolManConnectionHandle poolcon) {
		PreparedStatement ps = null;
		ResultSet rst = null;
		String sql = "select * from  ALM_TTTASK  where TASK_ID = ?";
		try {

			ps = poolcon.getPhysicalConnection().prepareStatement(sql);
			ps.setString(1, "t_1");
			rst = ps.executeQuery();
			while (rst.next()) {
				System.out.println(rst.getString(2));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void insertAlarm2DB(PoolManConnectionHandle poolcon) {
		String sql = "insert into ALM_RESULT (notificationid, motypeid, moentityid, thresholdtype, eventtime, probablecause, thresholdname,thresholdid, perceivedseverity, trendindication, thresholdinfo1, thresholdinfo2, thresholdinfo3,thresholdinfo4, thresholdinfo5, correlatednotifications, perceivedseveritynote, monitoredattributevalue1,monitoredattributevalue2, monitoredattributevalue3, monitoredattributevalue4, monitoredattributevalue5,thresholdtext,thresholdtext2,thresholdtext3,thresholdtext4,thresholdtext5, sendtime, timeoffset, sendstate, repeatalarmflag, clearflag, clearnotificationid,cleareventtime, period, location, acktime, operator, intergratedrate, confirmacktime, confirmoperator,confirmflag, confirmacktext, clearoperator, clearacktext, create_time, forward_tkt, tkt_id, moentityname, motypename,domainname,csvstate,xmlstate,syslogstate,compressalarmcount,forwardname) "
				+ " values (?,?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),"
				+ "?,?,?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?,?,'',"
				+ "?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?," // »∑»œ»À clrby
				+ 0
				+ ",'',?,'',to_date(?,'YYYY-MM-DD HH24:MI:SS'),"
				+ "?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = poolcon.getPhysicalConnection()
					.prepareStatement(sql);
			ps.setString(1, "notificationid");
			ps.setString(2, "motypeid");
			ps.setString(3, "moentityid");
			ps.setString(4, "thresholdtype");
			ps.setString(5, "2001-1-1");//eventTime
			ps.setString(6, "probablecause");
			ps.setString(7, "thresholdname");
			ps.setString(8, "thresholdid");
			ps.setString(9, "perceivedseverity");
			ps.setString(10, "trendindication");
			ps.setString(11, "thresholdinfo1");
			ps.setString(12, "thresholdinfo2");
			ps.setString(13, "thresholdinfo3");
			ps.setString(14, "thresholdinfo4");
			ps.setString(15, "thresholdinfo5");
			ps.setString(16, "correlatednotifications");
			ps.setString(17, "perceivedseveritynote");
			ps.setString(18, "monitoredattributevalue1");
			ps.setString(19, "monitoredattributevalue2");
			ps.setString(20, "monitoredattributevalue3");
			ps.setString(21, "monitoredattributevalue4");
			ps.setString(22, "monitoredattributevalue5");
			ps.setString(23, "thresholdtext");
			ps.setString(24, "thresholdtext2");
			ps.setString(25, "thresholdtext3");
			ps.setString(26, "thresholdtext4");
			ps.setString(27, "thresholdtext5");
			ps.setString(28, "2001-1-2");//sendTime
			ps.setString(29, "timeoffset");
			ps.setInt(30, 1);//sendflag
			ps.setString(31, "11");//repeatAlarmCount
			ps.setInt(32, 2);//clrflag
			ps.setString(33, "clearnotificationid");
			ps.setString(34, "2001-1-3");//clrtime
			ps.setString(35, "period");
			ps.setString(36, "location");
			ps.setString(37, "operator");
			ps.setString(38, "4");//intergratedRate
			ps.setString(39, "2001-1-4");//acktime
			ps.setString(40, "ackby");
			ps.setString(41, "clrby");
			ps.setString(42, "2001-1-6");//createTime
			ps.setInt(43, 3);//forwardtkt
			ps.setString(44, "tktid");
			ps.setString(45, "moEntityName");
			ps.setString(46, "moTypeName");
			ps.setString(47, "domainName");
			ps.setInt(48, 5);//csvState
			ps.setInt(49, 6);//xmlState
			ps.setInt(50, 7);//syslogState
			ps.setString(51, "8");//compressAlarmCount
			ps.setString(52, "forwardName");
			ps.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
