package PhoneLogDemo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements WritableComparable<FlowBean> {

    private long upFLow;
    private long downFlow;
    private long sumFlow;
    private long phoneNum;

    public FlowBean() {
        super();
    }

    public FlowBean(long upFLow, long downFlow, long sumFlow) {
        this.upFLow = upFLow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public FlowBean(long upFLow, long downFlow, long sumFlow, long phoneNum) {
        this.upFLow = upFLow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
        this.phoneNum = phoneNum;
    }

    public long getUpFLow() {
        return upFLow;
    }

    public void setUpFLow(long upFLow) {
        this.upFLow = upFLow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "upFLow=" + upFLow +
                ", downFlow=" + downFlow +
                ", sumFlow=" + sumFlow +
                ", phoneNum=" + phoneNum +
                '}';
    }

    @Override
    public int compareTo(FlowBean o) {
        return this.getPhoneNum()> o.getPhoneNum() ? -1 : -1;
    }

    //    序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFLow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
        dataOutput.writeLong(phoneNum);
    }

    //    反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        //要按照序列化的顺序进行反序列化
        this.upFLow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
        this.phoneNum = dataInput.readLong();
    }

}
