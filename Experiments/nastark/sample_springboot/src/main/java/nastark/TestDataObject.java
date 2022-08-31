package nastark;

import java.util.List;

public class TestDataObject {
    
    private final long num;
    private final String str;
    private final List<String> list;

    public TestDataObject(long num, String str, List<String> list) {
        this.num = num;
        this.str = str;
        this.list = list;
    }

    public long getNum() {
        return num;
    }

    public String getStr() {
        return str;
    }

    public List<String> getList() {
        return list;
    }

}
