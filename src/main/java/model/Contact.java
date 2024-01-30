package model;

public class Contact{
        private int contact_ID;
        private String name;
        public Contact(int contact_ID,String name){
            this.contact_ID = contact_ID;
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
        public int getContact_ID(){
            return contact_ID;
        }
}
