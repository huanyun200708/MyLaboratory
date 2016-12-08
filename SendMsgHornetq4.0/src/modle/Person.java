package modle;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person implements Serializable {  
	  
    private static final long serialVersionUID = 2670718766927459001L;  
    private Integer id;  
    private String name;  
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
    private String time = format.format(new Date());  
  
    /** 
     * @param id 
     * @param name 
     */  
    public Person(Integer id, String name) {  
        super();  
        this.id = id;  
        this.name = name;  
    }  
  
    /** 
     * @return the id 
     */  
    public Integer getId() {  
        return id;  
    }  
  
    /** 
     * @param id 
     *            the id to set 
     */  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
    /** 
     * @return the name 
     */  
    public String getName() {  
        return name;  
    }  
  
    /** 
     * @param name 
     *            the name to set 
     */  
    public void setName(String name) {  
        this.name = name;  
    }  
  
  
  
      
  
    /* (non-Javadoc) 
     * @see java.lang.Object#toString() 
     */  
    @Override  
    public String toString() {  
        return "Person [id=" + id + ", name=" + name + ", time=" + time + "]";  
    }  
  
    /* 
     * (non-Javadoc) 
     *  
     * @see java.lang.Object#hashCode() 
     */  
    @Override  
    public int hashCode() {  
        final int prime = 37;  
        int result = 17;  
        result = prime * result + ((id == null) ? 0 : id.hashCode());  
        result = prime * result + ((name == null) ? 0 : name.hashCode());  
        return result;  
    }  
  
    /* 
     * (non-Javadoc) 
     *  
     * @see java.lang.Object#equals(java.lang.Object) 
     */  
    @Override  
    public boolean equals(Object obj) {  
        if (this == obj)  
            return true;  
        if (obj == null)  
            return false;  
        if (getClass() != obj.getClass())  
            return false;  
        Person other = (Person) obj;  
        if (id == null) {  
            if (other.id != null)  
                return false;  
        } else if (!id.equals(other.id))  
            return false;  
        if (name == null) {  
            if (other.name != null)  
                return false;  
        } else if (!name.equals(other.name))  
            return false;  
        return true;  
    }  
  
}  