package personal.dgvu.util;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import java.util.Date;

public class PerformanceMonitor
{
	private final Log log = LogFactory.getLog(getClass());
	private final String MONITOR = "PERFORMANCE_MONITOR";
	private Monitor monitor;

	public void startMonitor()
	{
		monitor = MonitorFactory.start(MONITOR);
	}

	public void stopMonitor()
	{
		monitor.stop();
	}

	public void log(JoinPoint joinPoint_p)
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n");
		sb.append("*======================================");
		sb.append("\n");
		sb.append("*       PERFORMANCE STATISTICS        *");
		sb.append("\n");
		sb.append("*======================================");
		sb.append("\n");
		sb.append("*  JoinPoint: " + joinPoint_p.toLongString());
		sb.append("\n");
		sb.append("*  Method: " + joinPoint_p.getSignature().toLongString());
		sb.append("\n");
		sb.append("*  Execution Date: ").append(this.getLastAccess());
		sb.append("\n");
		sb.append("*  Last Execution Time: ").append(this.getLastCallTime()).append(" sec");
		sb.append("\n");
		sb.append("*  Service Calls: ").append(((this.getCallCount())));
		sb.append("\n");
		sb.append("*  Avg Execution Time: ").append(this.getAverageCallTime()).append(" sec");
		sb.append("\n");
		sb.append("*  Total Execution TIme: ").append(this.getTotalCallTime()).append(" sec");
		sb.append("\n");
		sb.append("*  Min Execution Time: ").append(this.getMinimumCallTime()).append(" sec");
		sb.append("\n");
		sb.append("*  Max Execution Time: ").append(this.getMaximumCallTime()).append(" sec");
		sb.append("\n");
		sb.append("*======================================");

		log.info(sb.toString());
	}


	/**
	 * get last access
	 *
	 * @return Date
	 */
	public Date getLastAccess()
	{
		return monitor.getLastAccess();
	}

	/**
	 * get call count
	 *
	 * @return int
	 */
	public int getCallCount()
	{
		return (int) monitor.getHits();
	}

	/**
	 * get average call time
	 *
	 * @return double
	 */
	public double getAverageCallTime()
	{
		return monitor.getAvg() / 1000;
	}

	/**
	 * get last call time
	 *
	 * @return double
	 */
	public double getLastCallTime()
	{
		return monitor.getLastValue() / 1000;
	}

	/**
	 * get maximum call time
	 *
	 * @return double
	 */
	public double getMaximumCallTime()
	{
		return monitor.getMax() / 1000;
	}

	/**
	 * get minimum call time
	 *
	 * @return double
	 */
	public double getMinimumCallTime()
	{
		return monitor.getMin() / 1000;
	}

	/**
	 * get total call time
	 *
	 * @return double
	 */
	public double getTotalCallTime()
	{
		return monitor.getTotal() / 1000;
	}
}