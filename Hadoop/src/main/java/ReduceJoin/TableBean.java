package ReduceJoin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TableBean implements Writable {
    private String order_id;
    private String p_id;
    private String p_Name;
    private int amount;
    private String table_source;

    public TableBean() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_Name() {
        return p_Name;
    }

    public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTable_source() {
        return table_source;
    }

    public void setTable_source(String table_source) {
        this.table_source = table_source;
    }

    @Override
    public String toString() {
        return  order_id + '\t' +
                p_id + '\t' +
                p_Name + '\t' +
                amount + '\t' +
                table_source + '\t';
    }


    @Override
    public void write(DataOutput Out) throws IOException {
        Out.writeUTF(order_id);
        Out.writeUTF(p_id);
        Out.writeInt(amount);
        Out.writeUTF(p_Name);
        Out.writeUTF(table_source);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this .order_id = input.readUTF();
        this.p_id = input.readUTF();
        this.amount = input.readInt();
        this.p_Name = input.readUTF();
        this.table_source = input.readUTF();
    }
}
