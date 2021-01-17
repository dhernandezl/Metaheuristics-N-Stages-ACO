package Utils;

public class Time {
	
	private Runtime runTime;
	private double time;
	private long timeStart;
	private long timeEnd;
	
	public Time() {
		this.runTime = Runtime.getRuntime();
	}
	
	public void initTime() {
		this.timeStart = System.currentTimeMillis();
	}
	
	public void endTime() {
		this.timeEnd = System.currentTimeMillis();
		this.time = this.timeEnd - this.timeStart;
	}
	
	public double getTimeHours() {
		return this.time * 1/1000 * 1/60 * 1/60;
	}
	
	public double getTimeMinutes() {
		return this.time * 1/1000 * 1/60;
	}
	
	public double getTimeSeconds() {
		return this.time * 1/1000;
	}
	
	public String getTimeFormat() {
		String hours = String.valueOf(getTimeHours());
		String minutes = String.valueOf(Double.valueOf(hours.substring(hours.indexOf("."), hours.length())) * 60);
		String seconds = String.valueOf(Double.valueOf(minutes.substring(minutes.indexOf("."), minutes.length())) * 60);
		String miliseconds =  String.valueOf(Math.round(Double.valueOf(seconds.substring(seconds.indexOf(".") + 1, seconds.length()))));
		return hours.substring(0,hours.indexOf(".")) + ":" + minutes.substring(0,minutes.indexOf(".")) +
				":" + seconds.substring(0,seconds.indexOf(".")) + ":" + miliseconds;
	}
}
