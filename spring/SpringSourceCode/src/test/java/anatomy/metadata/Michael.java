package anatomy.metadata;

@People
public class Michael {

    private String name;
    private int age;

    public Michael(){

    }

    public Michael(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

@Person
class Jack extends Michael{

}
