package sample;

import java.util.ArrayList;

public class Process implements Comparable<Process> {


    private double arrivalTime;
    private long processID;
    private long CpuBurst;
    private ArrayList CPUBurst = new ArrayList();
    private ArrayList IOBurst = new ArrayList();

    public Process(double arrivalTime, long processID, long cpuBurst, ArrayList CPUBurst, ArrayList IOBurst) {
        this.arrivalTime = arrivalTime;
        this.processID = processID;
        CpuBurst = cpuBurst;
        this.CPUBurst = CPUBurst;
        this.IOBurst = IOBurst;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public long getProcessID() {
        return processID;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessID(long processID) {
        this.processID = processID;
    }

    public long getCpuBurst() {
        return CpuBurst;
    }

    public void setCpuBurst(long cpuBurst) {
        CpuBurst = cpuBurst;
    }

    public ArrayList getCPUBurst() {
        return CPUBurst;
    }

    public void setCPUBurst(ArrayList CPUBurst) {
        this.CPUBurst = CPUBurst;
    }

    public ArrayList getIOBurst() {
        return IOBurst;
    }

    public void setIOBurst(ArrayList IOBurst) {
        this.IOBurst = IOBurst;
    }

    @Override
    public String toString() {
        return "Process{" +
                "arrivalTime=" + arrivalTime +
                ", processID=" + processID +
                ", CpuBurst=" + CpuBurst +
                ", CPUBurst=" + CPUBurst +
                ", IOBurst=" + IOBurst +
                '}';
    }

    @Override
    public int compareTo(Process o) {
        return Double.compare(this.arrivalTime, o.getArrivalTime());
    }
}
