import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/*
Errors may occur when
1. User inputs extra line
2. User adds extra comma
3. Family members submit separate google form
 */

public class Main {
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        boolean cont = true;
        while (cont) {
        System.out.println("What would you like to know? ");
        System.out.print(" 1. In-person - Head counts \n 2. In-person - Head Counts & Names \n 3. Online - Head Counts \n 4. Online - Head Counts & Names \n 5. Meals - Head Counts \n 6. Meals - Head Counts & Names \n 7. Group Sharing - Head Counts \n 8. Group Sharing - Head Counts & Names \n 9. All families \n 10. Each group (not including families) that lives under the same roof \n");
        System.out.println(" 11. In-person and Online kids");
        System.out.print("Enter a number: ");
        int choice = Integer.parseInt(input.next());
            switch (choice) {
                case 1:
                    System.out.println();
                    retDemographic("all");
                    break;
                case 2:
                    System.out.println();
                    retDemographic("all");
                    retNames("allInPerson");
                    break;
                case 3:
                    System.out.println();
                    retDemographic("online");
                    break;
                case 4:
                    System.out.println();
                    retDemographic("online");
                    retNames("allOnline");
                    break;
                case 5:
                    System.out.println();
                    retMealInfo(false);
                    break;
                case 6:
                    System.out.println();
                    retMealInfo(true);
                    break;
                case 7:
                    System.out.println();
                    retGroupSharing(false);
                    break;
                case 8:
                    System.out.println();
                    retGroupSharing(true);
                    break;
                case 9:
                    System.out.println();
                    System.out.print("Print a time of day, E.g. 6/18Noon, 6/18Night, 6/19Noon: ");
                    String time = input.next();
                    retFamilies(time);
                    break;
                case 10:
                    System.out.println();
                    System.out.print("Print a time of day, E.g. 6/18Noon, 6/18Night, 6/19Noon: ");
                    String time2 = input.next();
                    retHousemates(time2);
                    break;
                case 11:
                    System.out.println();
                    int totalInPerson = getNumKids("inPersonKids");
                    System.out.println("Total in person: " + totalInPerson);
                    retNames("inPersonKids");
                    System.out.println();
                    int totalOnline = getNumKids("onlineKids");
                    System.out.println("Total online: " + totalOnline);
                    retNames("onlineKids");
                    break;
            }
            System.out.println();
            System.out.print("Do you want to know more? Enter Yes or No: ");
            System.out.println();
            String choice1 = input.next();
            if (choice1.equals("No"))
                cont = false;
        }
    }

    public static ArrayList<Person> retListOfPeople() { //stores everyone's information into a list
        ArrayList<Person> list = new ArrayList();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\steph\\Downloads\\TCCA Retreat 2022 (Responses) - Form Responses.csv"));

            CSVReader csvReader = new CSVReader(br);
            List<String[]> listOfFamilies;
            listOfFamilies = csvReader.readAll();
            listOfFamilies.remove(0); // remove the title line
            br.close();
            csvReader.close();
            for (String[] tempArr: listOfFamilies) {
                int cutStartInd = 14;
                String address = tempArr[86];
                boolean anotherPerson = tempArr[13].contains("Yes");
                Person person = new Person(tempArr[3], tempArr[4], tempArr[5].contains("female") ? "female" : "male", Integer.parseInt(tempArr[6]), tempArr[7].contains("in-person") ? "in-person" : "online", tempArr[9].contains("Yes"), null, address);
                person.addMealInfo("6/18Noon", tempArr[10].contains("Yes"));
                person.addMealInfo("6/18Night", tempArr[11].contains("Yes"));
                person.addMealInfo("6/19Noon", tempArr[12].contains("Yes"));
                list.add(person);
                while (anotherPerson) {
                    Person newPerson = new Person(tempArr[cutStartInd], tempArr[cutStartInd+1], tempArr[cutStartInd + 2].contains("female") ? "female" : "male", Integer.parseInt(tempArr[cutStartInd + 4]), tempArr[cutStartInd + 5].contains("in-person") ? "in-person" : "online", tempArr[cutStartInd + 7].contains("Yes"), null, address);
                    newPerson.addMealInfo("6/18Noon", tempArr[cutStartInd + 8].contains("Yes"));
                    newPerson.addMealInfo("6/18Night", tempArr[cutStartInd + 9].contains("Yes"));
                    newPerson.addMealInfo("6/19Noon", tempArr[cutStartInd + 10].contains("Yes"));
                    list.add(newPerson);
                    cutStartInd += 12;
                    anotherPerson = tempArr[cutStartInd-1].contains("Yes");
                }
            }

//            String line;
//            br.readLine();
//            while ((line = br.readLine()) != null) {
//                int cutStartInd = 14;
//                String[] tempArr = line.split(",");
//                boolean anotherPerson = tempArr[13].contains("Yes");
//                Person person = new Person(tempArr[3], tempArr[4], tempArr[5].contains("female") ? "female" : "male", Integer.parseInt(tempArr[6]), tempArr[7].contains("in-person") ? "in-person" : "online", tempArr[9].contains("Yes"), null);
//                person.addMealInfo("6/18Noon", tempArr[10].contains("Yes"));
//                person.addMealInfo("6/18Night", tempArr[11].contains("Yes"));
//                person.addMealInfo("6/19Noon", tempArr[12].contains("Yes"));
//                list.add(person);
//                while (anotherPerson) {
//                    Person newPerson = new Person(tempArr[cutStartInd], tempArr[cutStartInd+1], tempArr[cutStartInd + 2].contains("female") ? "female" : "male", Integer.parseInt(tempArr[cutStartInd + 4]), tempArr[cutStartInd + 5].contains("in-person") ? "in-person" : "online", tempArr[cutStartInd + 7].contains("Yes"), null);
//                    newPerson.addMealInfo("6/18Noon", tempArr[cutStartInd + 8].contains("Yes"));
//                    newPerson.addMealInfo("6/18Night", tempArr[cutStartInd + 9].contains("Yes"));
//                    newPerson.addMealInfo("6/19Noon", tempArr[cutStartInd + 10].contains("Yes"));
//                    list.add(newPerson);
//                    cutStartInd += 12;
//                    anotherPerson = tempArr[cutStartInd-1].contains("Yes");
//                }
//            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void retDemographic(String category) { //returns requested demographic data
        ArrayList<Person> list = retListOfPeople();
        int onlineCount = 0;
        int adultsAndYouthCount = 0;
        int kidsCount = 0;
        int babiesCount = 0;
        for (Person person : list) {
            if (person.getMode().equals("online"))
                onlineCount++;
            else {
                int age = person.getAge();
                if (age >= 13)
                    adultsAndYouthCount++;
                else if (age >= 6 && age <= 12)
                    kidsCount++;
                else
                    babiesCount++;
            }
        }
        if (category.equals("online"))
            System.out.println("Total: " + onlineCount);
        else if (category.equals("all"))
            System.out.println("Total: " + (adultsAndYouthCount + kidsCount + babiesCount));
        else if (category.equals("adultsAndYouth"))
            System.out.println("Number of adults and youth (13 and up) attending in person: " + adultsAndYouthCount);
        else if (category.equals("kids"))
            System.out.println("Number of kids (6-12) attending in person: " + kidsCount);
        else if (category.equals("babies"))
            System.out.println("Number of babies (0-5) attending in person: " + babiesCount);
    }

    public static void retGroupSharing(boolean includeNames) { //returns number of people doing group sharing
        ArrayList<Person> list = retListOfPeople();
        int groupSharingCount = 0;
        int maleGroupSharingCount = 0;
        int femaleGroupSharingCount = 0;
        int kidGroupSharingCount = 0;
        for (Person person : list) {
            if (person.getGroupSharing() == true && person.getAge() <= 12) {
                groupSharingCount++;
                kidGroupSharingCount++;
            }
            else if (person.getGroupSharing() == true && person.getGender().equals("male")) {
                groupSharingCount++;
                maleGroupSharingCount++;
            }
            else if (person.getGroupSharing() == true && person.getGender().equals("female")) {
                groupSharingCount++;
                femaleGroupSharingCount++;
            }
        }
        if (!includeNames)
            System.out.println("Total - " + groupSharingCount + "; Male: " + maleGroupSharingCount + "; Female: " + femaleGroupSharingCount + "; Kids (12 and under): " + kidGroupSharingCount);
        else {
            System.out.println("Total - " + groupSharingCount + "; Male: " + maleGroupSharingCount + "; Female: " + femaleGroupSharingCount + "; Kids (12 and under): " + kidGroupSharingCount);
            System.out.println("Male: " + maleGroupSharingCount);
            for (Person person : list) {
                if (person.getGroupSharing() == true && person.getGender().equals("male") && person.getAge() > 12)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
            System.out.println("Female: " + femaleGroupSharingCount);
            for (Person person : list) {
                if (person.getGroupSharing() == true && person.getGender().equals("female") && person.getAge() > 12)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
            System.out.println("Kids (12 and under): " + kidGroupSharingCount);
            for (Person person : list) {
                if (person.getGroupSharing() == true && person.getAge() <= 12)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
    }

    public static void retMealInfo(boolean includeNames) { //returns meal information - number of people eating on what day
        if (!includeNames) {
            System.out.println("6/18 Lunch: Total - " + getNumOfPeople("all", "6/18Noon") + "; Male - " + getNumOfPeople("male", "6/18Noon") + "; Female - " + getNumOfPeople("female", "6/18Noon") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/18Noon"));
            System.out.println("6/18 Dinner: Total - " + getNumOfPeople("all", "6/18Night") + "; Male - " + getNumOfPeople("male", "6/18Night") + "; Female - " + getNumOfPeople("female", "6/18Night") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/18Night"));
            System.out.println("6/19 Lunch: Total - " + getNumOfPeople("all", "6/19Noon") + "; Male - " + getNumOfPeople("male", "6/19Noon") + "; Female - " + getNumOfPeople("female", "6/19Noon") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/19Noon"));
        }
        else {
            ArrayList<Person> list = retListOfPeople();
            System.out.println("6/18 Lunch: Total - " + getNumOfPeople("all", "6/18Noon") + "; Male - " + getNumOfPeople("male", "6/18Noon") + "; Female - " + getNumOfPeople("female", "6/18Noon") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/18Noon"));
            for (Person person : list) {
                if (person.getMealDict().get("6/18Noon") == true)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
            System.out.println("6/18 Dinner: Total - " + getNumOfPeople("all", "6/18Night") + "; Male - " + getNumOfPeople("male", "6/18Night") + "; Female - " + getNumOfPeople("female", "6/18Night") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/18Night"));
            for (Person person : list) {
                if (person.getMealDict().get("6/18Night") == true)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
            System.out.println("6/19 Lunch: Total - " + getNumOfPeople("all", "6/19Noon") + "; Male - " + getNumOfPeople("male", "6/19Noon") + "; Female - " + getNumOfPeople("female", "6/19Noon") + "; Kids (12 and under) - " + getNumOfPeople("kid", "6/19Noon"));
            for (Person person : list) {
                if (person.getMealDict().get("6/19Noon") == true)
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
    }

    public static int getNumOfPeople(String category, String dayAndTime) { //helper function for retMealInfo
        ArrayList<Person> list = retListOfPeople();
        int num = 0;
        for (Person person : list) {
            HashMap<String, Boolean> mealDict = person.getMealDict();
            if (category.equals("kid")) {
                if (mealDict.get(dayAndTime) == true && person.getAge() < 13)
                    num++;
            }

            else if (category.equals("male")) {
                if (mealDict.get(dayAndTime) == true && person.getAge() >= 13 && person.getGender().equals("male"))
                    num++;
            }
            else if (category.equals("female")) {
                if (mealDict.get(dayAndTime) == true && person.getAge() >= 13 && person.getGender().equals("female"))
                    num++;
            }
            else if (category.equals("all")) {
                if (mealDict.get(dayAndTime) == true)
                    num++;
            }
        }
        return num;
    }

    public static int getNumKids(String category) {
        ArrayList<Person> list = retListOfPeople();
        int num = 0;
        for (Person person : list) {
            if (category.equals("onlineKids")) {
                if (person.getAge() < 13 && person.getMode().equals("online"))
                    num++;
            } else if (category.equals("inPersonKids")) {
                if (person.getAge() < 13 && person.getMode().equals("in-person"))
                    num++;
            }
        }
        return num;
    }

    public static void retNames(String category) { // prints out names
        ArrayList<Person> list = retListOfPeople();
        if (category.equals("allOnline")) {
            for (Person person : list) {
                if (person.getMode().equals("online"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
        else if (category.equals("allInPerson")) {
            for (Person person : list) {
                if (person.getMode().equals("in-person"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
        else if (category.equals("male")) {
            for (Person person : list) {
                if (person.getGender().equals("male"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
        else if (category.equals("female")) {
            for (Person person : list) {
                if (person.getGender().equals("female"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
        else if (category.equals("onlineKids")) {
            for (Person person : list) {
                if (person.getAge() <= 12 && person.getMode().equals("online"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
        else if (category.equals("inPersonKids")) {
            for (Person person : list) {
                if (person.getAge() <= 12 && person.getMode().equals("in-person"))
                    System.out.println(person.getChineseName() + ", " + person.getEnglishName());
            }
        }
    }
    public static void retFamilies(String timeOfDay) {
        ArrayList<Family> famList = new ArrayList<>();
        int numFamilies = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\steph\\Downloads\\TCCA Retreat 2022 (Responses) - Form Responses.csv"));
            CSVReader csvReader = new CSVReader(br);
            List<String[]> listOfFamilies;
            listOfFamilies = csvReader.readAll();
            listOfFamilies.remove(0); // remove the title line
            br.close();
            csvReader.close();
            for (String[] tempArr: listOfFamilies) {
                boolean familyCreated = false;
                int cutStartInd = 14;
                boolean anotherPerson = tempArr[13].contains("Yes");
                String address = tempArr[86];

                //create person
                Person person = new Person(tempArr[3], tempArr[4], tempArr[5].contains("female") ? "female" : "male", Integer.parseInt(tempArr[6]), tempArr[7].contains("in-person") ? "in-person" : "online", tempArr[9].contains("Yes"), null, address);
                person.addMealInfo("6/18Noon", tempArr[10].contains("Yes"));
                person.addMealInfo("6/18Night", tempArr[11].contains("Yes"));
                person.addMealInfo("6/19Noon", tempArr[12].contains("Yes"));
                ArrayList<String> aList = new ArrayList<>();

                if (person.getMealDict().get(timeOfDay) && anotherPerson) { //first person is eating and there is another person in the family
                    aList.add(person.getChineseName() + ", " + person.getEnglishName());
                    int memberCount = 1;
                    Family newFamily = new Family(aList, memberCount);
                    while (anotherPerson) {
                        Person newPerson = new Person(tempArr[cutStartInd], tempArr[cutStartInd+1], tempArr[cutStartInd + 2].contains("female") ? "female" : "male", Integer.parseInt(tempArr[cutStartInd + 4]), tempArr[cutStartInd + 5].contains("in-person") ? "in-person" : "online", tempArr[cutStartInd + 7].contains("Yes"), null, address);
                        newPerson.addMealInfo("6/18Noon", tempArr[cutStartInd + 8].contains("Yes"));
                        newPerson.addMealInfo("6/18Night", tempArr[cutStartInd + 9].contains("Yes"));
                        newPerson.addMealInfo("6/19Noon", tempArr[cutStartInd + 10].contains("Yes"));
                        HashMap<String, Boolean> mealDict2 = newPerson.getMealDict();
                        if (mealDict2.get(timeOfDay)){
                            newFamily.addMember(newPerson.getChineseName() + ", " + newPerson.getEnglishName());
                            memberCount++;
                            newFamily.setMemberCount(memberCount);
                            familyCreated = true;
                        }
                        cutStartInd += 12;
                        anotherPerson = tempArr[cutStartInd-1].contains("Yes");
                    }
                    if (familyCreated) {
                        famList.add(newFamily);
                        numFamilies++;
                    }

                }
                else if (anotherPerson) { //first person is not eating but there is another person in the family
                    Person newPerson = new Person(tempArr[cutStartInd], tempArr[cutStartInd+1], tempArr[cutStartInd + 2].contains("female") ? "female" : "male", Integer.parseInt(tempArr[cutStartInd + 4]), tempArr[cutStartInd + 5].contains("in-person") ? "in-person" : "online", tempArr[cutStartInd + 7].contains("Yes"), null, address);
                    newPerson.addMealInfo("6/18Noon", tempArr[cutStartInd + 8].contains("Yes"));
                    newPerson.addMealInfo("6/18Night", tempArr[cutStartInd + 9].contains("Yes"));
                    newPerson.addMealInfo("6/19Noon", tempArr[cutStartInd + 10].contains("Yes"));
                    boolean willEat = false;
                    if (newPerson.getMealDict().get(timeOfDay)) {
                        willEat = true;
                    }
                    int memberCount = 1;
                    Family newFamily = new Family(aList, memberCount);
                    if (willEat) {
                        newFamily.addMember(newPerson.getChineseName() + ", " + newPerson.getEnglishName());
                    }
                    while (anotherPerson) {
                        newPerson = new Person(tempArr[cutStartInd], tempArr[cutStartInd+1], tempArr[cutStartInd + 2].contains("female") ? "female" : "male", Integer.parseInt(tempArr[cutStartInd + 4]), tempArr[cutStartInd + 5].contains("in-person") ? "in-person" : "online", tempArr[cutStartInd + 7].contains("Yes"), null, address);
                        newPerson.addMealInfo("6/18Noon", tempArr[cutStartInd + 8].contains("Yes"));
                        newPerson.addMealInfo("6/18Night", tempArr[cutStartInd + 9].contains("Yes"));
                        newPerson.addMealInfo("6/19Noon", tempArr[cutStartInd + 10].contains("Yes"));
                        if (newPerson.getMealDict().get(timeOfDay)){
                            newFamily.addMember(newPerson.getChineseName() + ", " + newPerson.getEnglishName());
                            memberCount++;
                            newFamily.setMemberCount(memberCount);
                            familyCreated = true;
                        }
                        cutStartInd += 12;
                        anotherPerson = tempArr[cutStartInd-1].contains("Yes");

                    }
                    if (familyCreated) {
                        famList.add(newFamily);
                        numFamilies++;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        System.out.println(timeOfDay);
        System.out.println("Total # of families: " + numFamilies);
        for (Family fam : famList) {
            ArrayList<String> lst = fam.getFamList();
            Iterator<String> iterator = lst.iterator();
            System.out.print("# of people: " + fam.getMemberCount() + " || ");
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + "|| ");
            }
            System.out.println();
        }
    }

    public static void retHousemates(String timeOfDay) {
        HashMap<String, ArrayList<Person>> housemateDict = new HashMap<>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\steph\\Downloads\\TCCA Retreat 2022 (Responses) - Form Responses.csv"));
            CSVReader csvReader = new CSVReader(br);
            List<String[]> listOfFamilies;
            listOfFamilies = csvReader.readAll();
            listOfFamilies.remove(0); // remove the title line
            br.close();
            csvReader.close();
            for (String[] tempArr: listOfFamilies) {
                if (tempArr[13].contains("Yes")) { //not including families
                    continue;
                }

                Person person = new Person(tempArr[3], tempArr[4], tempArr[5].contains("female") ? "female" : "male", Integer.parseInt(tempArr[6]), tempArr[7].contains("in-person") ? "in-person" : "online", tempArr[9].contains("Yes"), null, tempArr[86]);
                person.addMealInfo("6/18Noon", tempArr[10].contains("Yes"));
                person.addMealInfo("6/18Night", tempArr[11].contains("Yes"));
                person.addMealInfo("6/19Noon", tempArr[12].contains("Yes"));
                ArrayList<Person> lst = new ArrayList<>();
                lst.add(person);
                String address = tempArr[86];
                address = address.toLowerCase();
                address = address.trim();
                address = address.replaceAll("\\s", "");
                if (address.length() > 15) {
                    address = address.substring(0, 15);
                }
                HashMap<String, Boolean> mealDict = person.getMealDict();
                boolean eating = mealDict.get(timeOfDay) == true;
                if (!(housemateDict.containsKey(address)) && eating) {
                    housemateDict.put(address, lst);
                }
                else if (eating) {
                    ArrayList<Person> newLst = housemateDict.get(address);
                    newLst.add(person);
                    housemateDict.put(address, newLst);
                }
            }
            Iterator<String> addressIterator = housemateDict.keySet().iterator();
            for (ArrayList<Person> value : housemateDict.values()) {
                String address = addressIterator.next();
                if (value.size() < 2) {
                    continue;
                }
                else {
                    System.out.print(value.size() + " || ");
                    Iterator<Person> personIterator = value.iterator();
                    Iterator<Person> personIterator2 = value.iterator();
                    while (personIterator.hasNext()) {
                        Person person = personIterator.next();
                        System.out.print(person.getChineseName() + ", " + person.getEnglishName() + " || ");
                    }
                    System.out.print(address + "\n");
                    while (personIterator2.hasNext()) {
                        Person person = personIterator2.next();
                        System.out.print(person.getChineseName() + ", " + person.getEnglishName() + ", " + person.getAddress() + "\n");
                    }
                }
            }
        }
        catch (IOException | CsvException e)
        {
            e.printStackTrace();
        }
    }
}
