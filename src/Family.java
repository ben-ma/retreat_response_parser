import java.util.ArrayList;

public class Family {
    ArrayList<String> famList;
    int memberCount;
    public Family(ArrayList<String> list, int numMembers) {
        famList = list;
        memberCount = numMembers;
    }

    public void addMember(String person) {
        famList.add(person);
    }
    public ArrayList<String> getFamList() {
        return famList;
    }
    public void setFamList(ArrayList<String> newList) {
        famList = newList;
    }
    public int getMemberCount() {
        return memberCount;
    }
    public void setMemberCount(int newCount) {
        memberCount = newCount;
    }
}
