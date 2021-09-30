package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private ArrayList<Record> list = new ArrayList<>();

    public static void main(String[] args) {
        Record rec1 = new Record(123456789, "89137649297", "Victoria");
        Record rec2 = new Record(123455678, "89137649287", null);
        Record rec3 = new Record(123456670, "89139999999", "Blair");
        PhoneBook phoneBook1 = new PhoneBook(new ArrayList<>());
        try {
            phoneBook1.createRecord(rec1);
            phoneBook1.createRecord(rec2);
            phoneBook1.createRecord(rec3);
            System.out.println(phoneBook1.getAllRecords());
        }
        catch(PhoneNumberAlreadyExists ex){
            System.out.println(ex.getMessage());
        }
        try {
            phoneBook1.updateRecord(rec1);
        } catch (RecordNotValid e) {
            e.printStackTrace();
        }
        System.out.println(phoneBook1.getAllRecords());
        phoneBook1.deleteRecord(123456670);
        System.out.println(phoneBook1.getAllRecords());
    }

    public PhoneBook(ArrayList<Record> ls){
        list = ls;
    }

    public String getAllRecords(){
        String result = "";
        for(Record i: list){
           result += "Идентификатор: " + i.getId()+ ", Имя: " + i.getName() + ", Номер телефона: " +
                   i.getPhoneNumber() + "\n";
        }
        return result;
    }

    public void createRecord (Record record) throws PhoneNumberAlreadyExists{
        for(Record i: list){
            if(i.getPhoneNumber().equals(record.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExists("Телефонный номер" +
                        " уже существует");
            }
        }
        list.add(record);
    }

    public void updateRecord(Record record) throws RecordNotFound, RecordNotValid{
        Scanner in = new Scanner(System.in);
        System.out.print("Введите номер телефона: ");
        String num = in.nextLine();
        System.out.print("Введите имя: ");
        String namee = in.nextLine();
        if(num.equals("")  || namee.equals("")) throw new RecordNotValid("Не заполнены имя и/или номер телефона");
        int index = 0;
        for(Record i: list){
            if(i.getId() == record.getId()){
                i.setPhoneNumber(num);
                i.setName(namee);
            }else{
                index++;
            }
        }
        if (index == list.size()) throw new RecordNotFound("Не существующий номер");
    }

    public void deleteRecord(long id){
        int index = 0;
        for(Record i: list){
            if(i.getId() == id){
                list.remove(i);
                break;
            }else{
                index ++;
            }
        }
        if(index== list.size()) throw new RecordNotFound("Не существующий номер");
    }



    public static class Record {
        private long id;
        private String phoneNumber;
        private String name;

        public Record(long i, String pn, String n) {
            id = i;
            phoneNumber = pn;
            name = n;
        }

        @Override
        public String toString() { return id + " " + phoneNumber + " " + name;}
        public long getId(){ return this.id;}
        public String getPhoneNumber(){ return this.phoneNumber;}
        public String getName(){ return this.name;}
        public void setId(long id){
            this.id = id;
        }
        public void setPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
        }
        public void setName(String name){
            this.name = name;
        }

    }
}

class PhoneNumberAlreadyExists extends Exception{
    public PhoneNumberAlreadyExists(String message){
        super(message);
    }
}

class RecordNotValid extends Exception{
    public RecordNotValid(String message){
        super(message);
    }
}

class RecordNotFound extends RuntimeException{
    public RecordNotFound(String message){
        super(message);
    }
}