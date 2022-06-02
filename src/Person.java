import java.util.HashMap;

public class Person {
    String gender;
    int age;
    String mode;
    boolean groupSharing;
    HashMap<String, Boolean> mealDict;
    String chineseName;
    String englishName;
    String address;

    public Person(String chineseName, String englishName, String gender, int age, String mode, boolean groupSharing, HashMap<String, Boolean> mealDict, String address) {
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.gender = gender;
        this.age = age;
        this.mode = mode;
        this.groupSharing = groupSharing;
        this.mealDict = new HashMap<String, Boolean>();
        this.address = address;
    }

    public void addMealInfo(String date, boolean willEat) {
        mealDict.put(date, willEat);
    }
    public HashMap<String, Boolean> getMealDict() {
        return mealDict;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
    public String getChineseName() {
        return chineseName;
    }
    public String getEnglishName() {
        return englishName;
    }
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getMode() {
        return mode;
    }
    public void setGroupSharing(boolean groupSharing) {
        this.groupSharing = groupSharing;
    }
    public boolean getGroupSharing() {
        return groupSharing;
    }
}
