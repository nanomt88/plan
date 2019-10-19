package com.siyue.solar.solarsystem.entity;

import javax.persistence.*;

import java.io.Serializable;

/**  
 *  TODO add comment
 * @author nanomt88@gmail.com
 * @date 19.1.4 21:15
 * @updateLog: 
 *      TODO  update by nanomt88@gmail.com, 19.1.4 21:15
 */
@Entity(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 8655851615465363473L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer age;
    private String name;
    /**
     * TODO 忽略该字段的映射
     */
    @Transient
    private String  email;

    // TODO  省略get set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
