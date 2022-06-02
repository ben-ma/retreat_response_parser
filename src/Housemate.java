import java.util.ArrayList;

public class Housemate {
    ArrayList<String> memberList;
    int memberCount;
    public Housemate(ArrayList<String> list, int numMembers) {
        memberList = list;
        memberCount = numMembers;
    }

    public void addMember(String person) {
        memberList.add(person);
    }
    public ArrayList<String> getHousemateList() {
        return memberList;
    }
    public void setHousemateList(ArrayList<String> newList) {
        memberList = newList;
    }
    public int getMemberCount() {
        return memberCount;
    }
    public void setMemberCount(int newCount) {
        memberCount = newCount;
    }
}
