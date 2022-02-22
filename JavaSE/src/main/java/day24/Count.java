package day24;

/** 货币转账
 * @author 86155
 */
public class Count {
    private int count;
    private String amount;

    public Count() {}

    public Count(int count, String amount) {
        this.count = count;
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
